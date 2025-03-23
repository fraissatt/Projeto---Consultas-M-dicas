const API_CONSULTAS = "http://localhost:8080/consultas";

document.addEventListener("DOMContentLoaded", () => {
  carregarConsultas();

  document.getElementById("form-consulta").addEventListener("submit", async (e) => {
    e.preventDefault();
  
    // Limpar mensagens anteriores
    document.getElementById("erro-horario").innerText = "";
  
    const data = document.getElementById("data").value;
    const horario = document.getElementById("horario").value;
    const medicoId = document.getElementById("medico").value;
    const pacienteId = document.getElementById("paciente").value;
  
    const consulta = { data, horario, medicoId, pacienteId };
  
    try {
      const response = await fetch(`${API_CONSULTAS}/agendar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(consulta)
      });
  
      if (response.ok) {
        mostrarMensagem("Consulta agendada com sucesso!", "green");
        fecharModal();
        carregarConsultas();
      } else {
        const errorText = await response.text();
        
        if (errorText.includes("Já existe uma consulta agendada para essa data e horário")) {
          document.getElementById("erro-horario").innerText = "Já existe uma consulta nesse horário.";
        } else {
          mostrarMensagem("Erro ao agendar consulta: " + errorText, "red");
        }
      }
    } catch (error) {
      mostrarMensagem("Erro inesperado ao agendar consulta.", "red");
    }
  });
  
  
});

async function carregarConsultas() {
    const [respConsultas, respMedicos, respPacientes] = await Promise.all([
      fetch(API_CONSULTAS),
      fetch("http://localhost:8080/medicos"),
      fetch("http://localhost:8080/pacientes")
    ]);
  
    const [consultas, medicos, pacientes] = await Promise.all([
      respConsultas.json(),
      respMedicos.json(),
      respPacientes.json()
    ]);
  
    const mapaMedicos = {};
    const mapaPacientes = {};
  
    medicos.forEach(m => mapaMedicos[m.id] = m.nome);
    pacientes.forEach(p => mapaPacientes[p.id] = p.nome);
  
    const tabela = document.getElementById("tabela-consultas");
    tabela.innerHTML = "";
  
    consultas.forEach(c => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${c.id}</td>
        <td>${c.data}</td>
        <td>${c.horario}</td>
        <td>${formatarStatus(c.status)}</td>
        <td>${mapaMedicos[c.medicoId] || "Desconhecido"}</td>
        <td>${mapaPacientes[c.pacienteId] || "Desconhecido"}</td>
        <td>
        ${c.status === 'agendada' ? `
            <button class="btn-realizar" onclick="realizarConsulta(${c.id})">Realizar</button>
            <button class="btn-excluir" onclick="cancelarConsulta(${c.id})">Cancelar</button>
        ` : c.status === 'cancelada' ? `
            <button class="btn-remover" onclick="excluirConsulta(${c.id})">Excluir</button>
        ` : ''}
        </td>
      `;
      tabela.appendChild(tr);
    });
  }  

async function cancelarConsulta(id) {
  const response = await fetch(`${API_CONSULTAS}/${id}/cancelar`, {
    method: "PUT"
  });

  if (response.ok) {
    mostrarMensagem("Consulta cancelada.", "orange");
    carregarConsultas();
  } else {
    mostrarMensagem("Erro ao cancelar consulta.", "red");
  }
}

function abrirModal() {
  carregarMedicos();
  carregarPacientes();
  document.getElementById("modal").style.display = "block";
}

function fecharModal() {
  document.getElementById("modal").style.display = "none";
  document.getElementById("form-consulta").reset();
}

function mostrarMensagem(texto, cor) {
  const div = document.getElementById("mensagem-status");
  div.style.color = cor;
  div.innerText = texto;
  div.style.display = "block";
  setTimeout(() => div.style.display = "none", 3000);
}

window.onclick = function (e) {
  const modal = document.getElementById("modal");
  if (e.target === modal) {
    fecharModal();
  }
};

async function carregarMedicos() {
    const response = await fetch("http://localhost:8080/medicos");
    const data = await response.json();
  
    const selectMedico = document.getElementById("medico");
    selectMedico.innerHTML = ""; // Limpa opções anteriores
  
    data.forEach(medico => {
      const option = document.createElement("option");
      option.value = medico.id;
      option.textContent = medico.nome;
      selectMedico.appendChild(option);
    });
  }
  
  async function carregarPacientes() {
    const response = await fetch("http://localhost:8080/pacientes");
    const data = await response.json();
  
    const selectPaciente = document.getElementById("paciente");
    selectPaciente.innerHTML = "";
  
    data.forEach(paciente => {
      const option = document.createElement("option");
      option.value = paciente.id;
      option.textContent = paciente.nome;
      selectPaciente.appendChild(option);
    });
  }
  
  async function realizarConsulta(id) {
    const response = await fetch(`${API_CONSULTAS}/${id}/realizar`, { method: "PUT" });
    if (response.ok) {
      mostrarMensagem("Consulta marcada como realizada.", "green");
      carregarConsultas();
    } else {
      mostrarMensagem("Erro ao marcar como realizada.", "red");
    }
  }
  
  async function excluirConsulta(id) {
    const response = await fetch(`${API_CONSULTAS}/${id}`, { method: "DELETE" });
    if (response.ok) {
      mostrarMensagem("Consulta excluída com sucesso.", "orange");
      carregarConsultas();
    } else {
      mostrarMensagem("Erro ao excluir consulta.", "red");
    }
  }
  
  function formatarStatus(status) {
    if (status === 'realizada') {
      return 'Realizada ✅'; // ícone verde de concluído
    }
    return status.charAt(0).toUpperCase() + status.slice(1); // Agendada, Cancelada...
  }
  
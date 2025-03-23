const API_URL = "http://localhost:8080/medicos";

document.addEventListener("DOMContentLoaded", () => {
  carregarMedicos();

  // Evento do formulário
  document.getElementById("form-medico").addEventListener("submit", async (e) => {
    e.preventDefault();
    
    const id = document.getElementById("medico-id").value;
    const nome = document.getElementById("nome").value;
    const especialidade = document.getElementById("especialidade").value;
    const crm = document.getElementById("crm").value;

    const medico = { nome, especialidade, crm };

    if (id) {
      // Atualizar médico existente
      const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(medico)
      });

      if (response.ok) {
        exibirMensagem("Médico atualizado com sucesso!");
        fecharModal(); // 👈 Fechar modal após atualização
        carregarMedicos();
      } else {
        exibirMensagem("Erro ao atualizar médico.", "red");
      }
    } else {
      // Criar novo médico
      const response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(medico)
      });

      if (response.ok) {
        exibirMensagem("Médico cadastrado com sucesso!");
        fecharModal(); // 👈 Fechar modal após cadastro
        carregarMedicos();
      } else {
        exibirMensagem("Erro ao cadastrar médico.", "red");
      }
    }

    document.getElementById("form-medico").reset();
    document.getElementById("medico-id").value = "";
  });

  // Máscara para CRM
  document.getElementById("crm").addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, ''); // remove não numéricos
    if (value.length > 5) {
      value = value.slice(0, 5);
    }
    e.target.value = value ? `CRM-${value}` : "";
  });
});

async function carregarMedicos() {
  const response = await fetch(API_URL);
  const data = await response.json();

  const tabela = document.getElementById("tabela-medicos");
  tabela.innerHTML = "";

  data.forEach(medico => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${medico.id}</td>
      <td>${medico.nome}</td>
      <td>${medico.especialidade}</td>
      <td>${medico.crm}</td>
      <td>
        <button class="btn-editar" onclick="prepararEdicao(${medico.id}, '${medico.nome}', '${medico.especialidade}', '${medico.crm}')">Editar</button>
        <button class="btn-excluir" onclick="excluirMedico(${medico.id})">Excluir</button>
      </td>
    `;
    tabela.appendChild(tr);
  });
}

function prepararEdicao(id, nome, especialidade, crm) {
  document.getElementById("medico-id").value = id;
  document.getElementById("nome").value = nome;
  document.getElementById("especialidade").value = especialidade;
  document.getElementById("crm").value = crm;
  document.getElementById("titulo-modal").innerText = "Editar Médico";
  abrirModal();
}

async function excluirMedico(id) {
  if (confirm("Tem certeza que deseja excluir este médico?")) {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE"
    });

    if (response.ok) {
      carregarMedicos();
    } else {
      alert("Erro ao excluir médico.");
    }
  }
}

function abrirModal() {
  document.getElementById("modal").style.display = "block";
}

function fecharModal() {
  document.getElementById("modal").style.display = "none";
  document.getElementById("form-medico").reset();
  document.getElementById("medico-id").value = "";
  document.getElementById("titulo-modal").innerText = "Cadastrar Médico";
}

// Fecha o modal ao clicar fora da área de conteúdo
window.onclick = function(event) {
  const modal = document.getElementById("modal");
  if (event.target === modal) {
    fecharModal();
  }
}

function exibirMensagem(mensagem, cor = "green") {
  const msg = document.getElementById("mensagem-status");
  msg.textContent = mensagem;
  msg.style.color = cor;
  msg.style.display = "block";

  setTimeout(() => {
    msg.style.display = "none";
  }, 3000); // some após 3 segundos
}

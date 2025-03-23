const API_URL = "http://localhost:8080/pacientes";

document.addEventListener("DOMContentLoaded", () => {
  carregarPacientes();

  document.getElementById("form-paciente").addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = document.getElementById("paciente-id").value;
    const nome = document.getElementById("nome").value;
    const cpf = document.getElementById("cpf").value;
    const dataNascimento = document.getElementById("dataNascimento").value;
    const telefone = document.getElementById("telefone").value;

    const paciente = { nome, cpf, dataNascimento, telefone };

    try {
      let response;
      if (id) {
        response = await fetch(`${API_URL}/${id}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(paciente)
        });

        if (response.ok) {
          exibirMensagem("Paciente atualizado com sucesso!");
          fecharModal();
          carregarPacientes();
        } else {
          exibirMensagem("Erro ao atualizar paciente.", "red");
        }
      } else {
        response = await fetch(API_URL, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(paciente)
        });

        if (response.ok) {
          exibirMensagem("Paciente cadastrado com sucesso!");
          fecharModal();
          carregarPacientes();
        } else {
          exibirMensagem("Erro ao cadastrar paciente.", "red");
        }
      }
    } catch (error) {
      exibirMensagem("Erro de conexão com a API.", "red");
    }
  });

  // Máscaras frontend
  document.getElementById("cpf").addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, "");
    if (value.length > 11) value = value.slice(0, 11);
    value = value.replace(/(\d{3})(\d)/, "$1.$2")
                 .replace(/(\d{3})(\d)/, "$1.$2")
                 .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    e.target.value = value;
  });

  document.getElementById("telefone").addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, "");
    if (value.length > 11) value = value.slice(0, 11);
    value = value.replace(/(\d{2})(\d)/, "($1) $2")
                 .replace(/(\d{5})(\d{1,4})$/, "$1-$2");
    e.target.value = value;
  });
});

async function carregarPacientes() {
  const response = await fetch(API_URL);
  const data = await response.json();
  const tabela = document.getElementById("tabela-pacientes");
  tabela.innerHTML = "";

  data.forEach(paciente => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${paciente.id}</td>
      <td>${paciente.nome}</td>
      <td>${paciente.cpf}</td>
      <td>${paciente.dataNascimento}</td>
      <td>${paciente.telefone}</td>
      <td>
        <button class="btn-editar" onclick="prepararEdicao(${paciente.id}, '${paciente.nome}', '${paciente.cpf}', '${paciente.dataNascimento}', '${paciente.telefone}')">Editar</button>
        <button class="btn-excluir" onclick="excluirPaciente(${paciente.id})">Excluir</button>
      </td>
    `;
    tabela.appendChild(tr);
  });
}

function prepararEdicao(id, nome, cpf, dataNascimento, telefone) {
  document.getElementById("paciente-id").value = id;
  document.getElementById("nome").value = nome;
  document.getElementById("cpf").value = cpf;
  document.getElementById("dataNascimento").value = dataNascimento;
  document.getElementById("telefone").value = telefone;
  document.getElementById("titulo-modal").innerText = "Editar Paciente";
  abrirModal();
}

async function excluirPaciente(id) {
  if (confirm("Tem certeza que deseja excluir este paciente?")) {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE"
    });
    if (response.ok) {
      exibirMensagem("Paciente excluído com sucesso!");
      carregarPacientes();
    } else {
      exibirMensagem("Erro ao excluir paciente.", "red");
    }
  }
}

function abrirModal() {
  document.getElementById("modal").style.display = "block";
}

function fecharModal() {
  document.getElementById("modal").style.display = "none";
  document.getElementById("form-paciente").reset();
  document.getElementById("paciente-id").value = "";
  document.getElementById("titulo-modal").innerText = "Cadastrar Paciente";
}

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
  }, 3000);
}

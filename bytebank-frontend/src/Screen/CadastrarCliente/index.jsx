import { useState } from "react";
import { DefaultLabelInput } from "../../Components/DefaultLabelInput";
import Navbar from "../../Components/Navbar";
import "./styles.css";
import api from "../../service/api";

export const CadastrarCliente = () => {
  const [cliente, setCliente] = useState({
    nome: "",
    sobrenome: "",
  });
  const [endereco, setEndereco] = useState();

  const handleChangeClienteInput = (field, value) => {
    setCliente((prevForm) => ({
      ...prevForm,
      [field]: value,
    }));
  };

  const handleChangeEnderecoInput = (field, value) => {
    setEndereco((prevForm) => ({
      ...prevForm,
      [field]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await api.post("/clientes", {
        ...cliente,
        endereco: {
          ...endereco,
        } 
      });
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      <Navbar />
      <div className="customContainer">
        <h1>Cadastrar cliente</h1>

        <form className="containerForm" onSubmit={handleSubmit}>
          <DefaultLabelInput
            label="Nome"
            onChange={(event) =>
              handleChangeClienteInput("nome", event.target.value)
            }
          />
          <DefaultLabelInput
            label="Sobrenome"
            onChange={(event) =>
              handleChangeClienteInput("sobrenome", event.target.value)
            }
          />
          <DefaultLabelInput
            label="Date de nascimento"
            type="date"
            onChange={(event) =>
              handleChangeClienteInput("dataNascimento", event.target.value)
            }
          />
          <DefaultLabelInput
            label="Cpf"
            onChange={(event) =>
              handleChangeClienteInput("cpf", event.target.value)
            }
          />
          <DefaultLabelInput
            label="Email"
            onChange={(event) =>
              handleChangeClienteInput("email", event.target.value)
            }
          />
          <div className="containerFormEndereco">
            <DefaultLabelInput
              label="Rua"
              onChange={(event) =>
                handleChangeEnderecoInput("numero", event.target.value)
              }
            />
            <DefaultLabelInput
              label="Numero"
              onChange={(event) =>
                handleChangeEnderecoInput("rua", event.target.value)
              }
            />
          </div>
          <div className="containerFormEndereco">
            <DefaultLabelInput
              label="Bairro"
              onChange={(event) =>
                handleChangeEnderecoInput("bairro", event.target.value)
              }
            />
            <DefaultLabelInput
              label="Cep"
              onChange={(event) =>
                handleChangeEnderecoInput("cep", event.target.value)
              }
            />
          </div>
          <button type="submit" class="btn btn-primary">
            Enviar 
          </button>
        </form>
      </div>
    </>
  );
};

export default CadastrarCliente;

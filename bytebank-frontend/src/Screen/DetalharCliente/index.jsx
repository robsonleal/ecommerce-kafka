import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import ClienteCard from "../../Components/ClienteCard";
import { DefaultLabelInput } from "../../Components/DefaultLabelInput";
import api from "../../service/api";
import Navbar from "../../Components/Navbar";
import "./styles.css";

export default function DetalharCliente() {
  const { id } = useParams();
  const [cliente, setCliente] = useState();
  const [deposito, setDeposito] = useState();
  const [saque, setSaque] = useState();

  const handleClickDepositar = async () => {
    try {
      const result = await api.post(`/clientes/${id}/depositar`, null, {
        params: { valor: deposito.target.value },
      });
      alert(result.data.mensagem);
      return result;
    } catch (err) {
      console.error(err);
    }
  };

  const handleClickSacar = async () => {
    try {
      const result = await api.post(
        `/clientes/${id}/sacar?valor=${saque.target.value}`
      );
      alert("Saque realizado com sucesso!");
      return result;
    } catch (err) {
      alert(err.response.data.message);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get(`/clientes/${id}`);
        setCliente(response.data);
      } catch (err) {
        alert(err.message);
      }
    };

    fetchData();
  }, []);

  if (cliente == null) {
    return <h1>Carregando...</h1>;
  }

  return (
    <>
      <Navbar />
      <div className="container my-5 custom-container">
        <div className="container-cliente">
          <ClienteCard data={cliente} />
        </div>
        <div className="container-menu">
          <h1>Ações</h1>
          <div>
            <DefaultLabelInput label="Depositar" onChange={setDeposito} />
            <button
              type="submit"
              class="btn btn-primary"
              onClick={handleClickDepositar}
            >
              Depositar
            </button>
          </div>
          <div>
            <DefaultLabelInput label="Sacar" onChange={setSaque} />
            <button
              type="submit"
              class="btn btn-primary"
              onClick={handleClickSacar}
            >
              Sacar
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

import React from "react";
import { Route, Routes } from "react-router-dom";
import CadastrarCliente from "../Screen/CadastrarCliente";
import Home from "../Screen/Home";
import DetalharCliente from "../Screen/DetalharCliente";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/clientes" element={<CadastrarCliente />} />
      <Route path="/clientes/:id" element={<DetalharCliente />} />
    </Routes>
  );
}

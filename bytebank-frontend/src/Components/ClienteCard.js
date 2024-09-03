import { Link } from "react-router-dom";

function ClienteCard({ data }) {
  // const

  const handleClick = (clienteId) => {
    console.log(clienteId);
  };

  return (
    <div className="card mb-3">
      <div className="card-body" style={styles.cardStyles}>
        <div style={styles.cardTitleContainer}>
          <h5
            className="card-title"
            style={styles.title}
          >{`${data.nome} ${data.sobrenome}`}</h5>
        </div>
        <p className="card-text" style={styles.cardParagraph}>
          <strong style={styles.strongText}>Email: </strong>
          <em>{data.email}</em>
        </p>
        <p className="card-text" style={styles.cardParagraph}>
          <strong style={styles.strongText}>Cpf: </strong>
          <em>{data.cpf}</em>
        </p>
        <p className="card-text" style={styles.cardParagraph}>
          <strong style={styles.strongText}>Data de nascimento: </strong>
          <em>{data.dataNascimento}</em>
        </p>
        <p className="card-text" style={styles.cardParagraph}>
          <strong style={styles.strongText}>Endereço: </strong>
          <em>{`${data.endereco.rua}, ${data.endereco.numero} - ${data.endereco.bairro}`}</em>
        </p>
        <p className="card-text" style={styles.cardParagraph}>
          <strong style={styles.strongText}>Cep: </strong>
          <em>{data.endereco.cep}</em>
        </p>
        <p className="card-text" style={styles.cardParagraph}>
          <strong style={styles.strongText}>Conta Numero: </strong>
          <em>{data.contaNumero}</em>
        </p>
        <p className="card-text" style={styles.cardParagraph}>
          <strong style={styles.strongText}>Saldo: </strong>
          <em>{data.contaSaldo}</em>
        </p>
        <Link className="btn btn-primary" to={`/clientes/${data.id}`}>Acessar</Link>
      </div>
    </div>
  );
}

const styles = {
  cardStyles: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "flex-start",
    alignItems: "flex-start",
    backgroundColor: "#DDD",
  },
  cardTitleContainer: {
    borderBottom: "2px solid black",
    width: "100%",
    marginBottom: 12,
  },
  title: {
    fontSize: 24,
  },
  strongText: {
    textTransform: "uppercase",
  },
  cardParagraph: {
    display: "flex",
    alignItems: "flex-start",
    flexDirection: "column",
  },
};

export default ClienteCard;

import { useEffect, useState } from "react";
import ClienteCard from "../Components/ClienteCard";
import Navbar from "../Components/Navbar";
import api from "../service/api";

function Home() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get("/clientes");
        setData(response.data);
      } catch (err) {
        console.error(err);
      } finally {
        // setLoading(false);
      }
    };

    fetchData();
  }, []);

  console.log(data.length);

  return (
    <>
      <Navbar />
      <div className="container text-center" style={styles.container}>
        <div className="row">
          {data.length !== 0 ? (
            data.map((cliente, index) => (
              <div className="col">
                <ClienteCard key={index} data={cliente} />
              </div>
            ))
          ) : (
            <h1>Carregando...</h1>
          )}
        </div>
      </div>
    </>
  );
}

const styles = {
  container: {
    paddingBlock: 15,
    paddingInline: "5%",
  },
};

export default Home;

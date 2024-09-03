import { Link, useLocation } from "react-router-dom";

function Navbar() {
  const location = useLocation();

  return (
    <nav class="navbar bg-primary navbar-expand-lg" data-bs-theme="dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">
          Bytebank
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarTogglerDemo02"
          aria-controls="navbarTogglerDemo02"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <Link 
                to='/' 
                className={`nav-link ${location.pathname === '/' ? 'active' : '' } `}
              >
                Inicio
              </Link>
            </li>
            <li class="nav-item">
              <Link to='/clientes' className={`nav-link ${location.pathname === '/clientes' ? 'active' : '' }`} >
                Cadastrar Cliente
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}



export default Navbar;

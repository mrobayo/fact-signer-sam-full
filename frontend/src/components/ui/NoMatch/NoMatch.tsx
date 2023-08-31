import { Link } from "react-router-dom";

function NoMatch() {
  return (
    <div>
      <h2>Ruta no existe!</h2>
      <p>
        <Link to="/">Ir a la pagina de inicio</Link>
      </p>
    </div>
  );
}

export default NoMatch;

package com.marvic.factsigner.model.sistema.types;

public enum StockTipo {

    SERVICIO,		//NO LLEVA control de stock
    ALMACENABLE,	//Lleva una reposición en automatizada, pudiendose definir reglas de abastecimiento
    CONSUMIBLE;		//NO hay control del nivel de existencias

}

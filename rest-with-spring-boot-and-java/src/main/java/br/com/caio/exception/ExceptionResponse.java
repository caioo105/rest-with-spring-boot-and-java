package br.com.caio.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}

//esse record serve para retornar um Json detalhado e amigavel ao inves da exception atual.
package com.jurai.data.model;

public record ChatMessage(String contents, boolean AIMessage, Exception error) {}
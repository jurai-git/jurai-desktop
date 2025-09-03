package com.jurai.data.model;

public record ChatMessage(String contents, String messageType, boolean AIMessage, Exception error) {}
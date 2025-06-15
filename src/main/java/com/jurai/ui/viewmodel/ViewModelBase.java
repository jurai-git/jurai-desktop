package com.jurai.ui.viewmodel;

import com.jurai.data.AppState;

public abstract class ViewModelBase {
    protected final AppState appState = AppState.get();
}

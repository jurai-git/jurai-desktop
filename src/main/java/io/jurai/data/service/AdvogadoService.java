package io.jurai.data.service;

import io.jurai.data.ApplicationState;
import io.jurai.data.model.Advogado;
import io.jurai.util.EventLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdvogadoService implements Service<Advogado> {

    private static ArrayList<Advogado> existingAdvogados = new ArrayList<>();

    @Override
    public List<Advogado> getAll() {
        return existingAdvogados;
    }

    @Override
    public void create(Advogado adv) {
        existingAdvogados.add(adv);
    }

    @Override
    public void remove(Advogado adv) {
        existingAdvogados.remove(adv);

    }

    public void setCurrent(Advogado advogado) {
        if(advogado == null) {
            EventLogger.logWarning("tried to log-in with non-existing user");
            throw new IllegalArgumentException("Este advogado não existe!");
        }

        Optional<Advogado> found = existingAdvogados.stream().filter(a -> a.equals(advogado)).findFirst();

        found.ifPresentOrElse(ApplicationState::setCurrentUser, () -> {
            EventLogger.logWarning("tried to log-in with non-existing user");
            throw new IllegalArgumentException("Este advogado não existe!");
        });
    }

    public void authenticate(String email, String password) {
        existingAdvogados.stream().filter(adv -> {
            if(adv.getEmail().equals(email) && adv.getSenha().equals(password)) {
                return true;
            }
            return false;
        }).findFirst().ifPresentOrElse(this::setCurrent, () -> {
            EventLogger.logWarning("tried to log-in with non-existing user");
            throw new IllegalArgumentException("Este advogado não existe!");
        });
    }

    public Advogado getCurrent() {
        return ApplicationState.getCurrentUser();
    }
}

package com.jurai.ui.controller;

import com.jurai.data.model.Requerente;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.ui.modal.RequerenteModal;
import com.jurai.ui.modal.RequerenteRegisterModal;
import javafx.scene.control.Alert;

public class RequerenteRegisterModalController extends AbstractController<RequerenteRegisterModal> {
    private final AdvogadoService advogadoService = AdvogadoService.getInstance();

    @Override
    protected void attachEvents(RequerenteRegisterModal pane) {
        pane.getPersonalInfoNext().setOnAction(e -> {
            pane.setActiveTab(pane.getGeneralInfo());
        });
        pane.getGeneralInfoPrevious().setOnAction(e -> {
            pane.setActiveTab(pane.getPersonalInfo());
        });
        pane.getGeneralInfoNext().setOnAction(e -> {
            pane.setActiveTab(pane.getAddressInfo());
        });
        pane.getAddressInfoPrevious().setOnAction(e -> {
            pane.setActiveTab(pane.getGeneralInfo());
        });
        pane.getCancel().forEach(button -> button.setOnAction(e -> {
            pane.dispose();
        }));

        pane.getCreate().setOnAction(e -> {
            Requerente r = new Requerente(
                    pane.getCpfCnpj(),
                    pane.getNome(),
                    pane.getNomeSocial(),
                    pane.getGenero(),
                    pane.isIdoso(),
                    pane.getRg(),
                    pane.getOrgaoEmissor(),
                    pane.getEstadoCivil(),
                    pane.getNacionalidade(),
                    pane.getProfissao(),
                    pane.getCep(),
                    pane.getLogradouro(),
                    pane.getEmail(),
                    pane.getNumero(),
                    pane.getComplemento(),
                    pane.getBairro(),
                    pane.getEstado(),
                    pane.getCidade()
            );
            try {
                advogadoService.addRequerente(r);
                pane.clear();
                pane.dispose();
            } catch (ResponseNotOkException ex) {
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }


    @Override
    protected void attachNotifiers(RequerenteRegisterModal pane) {

    }
}

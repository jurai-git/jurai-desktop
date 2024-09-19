package com.jurai.ui.controller;

import com.jurai.data.model.Requerente;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.modal.RequerenteEditingModal;
import com.jurai.ui.modal.RequerenteRegisterModal;
import javafx.scene.control.Alert;

public class RequerenteEditingModalController extends AbstractController<RequerenteEditingModal> {
    private final RequerenteService requerenteService = RequerenteService.getInstance();

    @Override
    protected void attachEvents(RequerenteEditingModal pane) {
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
            try {
                Requerente r = pane.getObject();
                r.setCpfCnpj(pane.getCpfCnpj());
                r.setNome(pane.getNome());
                r.setNomeSocial(pane.getNomeSocial());
                r.setGenero(pane.getGenero());
                r.setIdoso(pane.isIdoso());
                r.setRg(pane.getRg());
                r.setOrgaoEmissor(pane.getOrgaoEmissor());
                r.setEstadoCivil(pane.getEstadoCivil());
                r.setNacionalidade(pane.getNacionalidade());
                r.setProfissao(pane.getProfissao());
                r.setCep(pane.getCep());
                r.setLogradouro(pane.getLogradouro());
                r.setEstado(pane.getEstado());
                r.setEmail(pane.getEmail());
                r.setNumImovel(pane.getNumero());
                r.setComplemento(pane.getComplemento());
                r.setBairro(pane.getBairro());
                r.setCidade(pane.getCidade());
                requerenteService.update(r);
                pane.clear();
                pane.dispose();
            } catch (ResponseNotOkException ex) {
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

    }

    @Override
    protected void attachNotifiers(RequerenteEditingModal pane) {

    }
}

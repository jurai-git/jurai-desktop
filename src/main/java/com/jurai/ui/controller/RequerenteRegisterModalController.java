package com.jurai.ui.controller;

import com.jurai.data.model.Requerente;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.ui.modal.RequerenteModal;
import com.jurai.ui.modal.RequerenteRegisterModal;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;
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
            try {
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
                advogadoService.addRequerente(r);
                pane.clear();
                pane.dispose();
            } catch (NullPointerException ex) {
                new DefaultMessageNotification("Você deixou algum campo em branco!", NotificationType.ERROR).show();
                ex.printStackTrace();
            } catch (ResponseNotOkException ex) {
                new DefaultMessageNotification("Erro ao comunicar com o servidor!", NotificationType.ERROR).show();
                ex.printStackTrace();
            }
        });
    }


    @Override
    protected void attachNotifiers(RequerenteRegisterModal pane) {

    }
}

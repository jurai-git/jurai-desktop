package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.ui.viewmodel.LoginMenuVM;
import dev.mgcvale.fluidfx.components.controls.*;
import dev.mgcvale.fluidfx.components.groups.BorderGroup;
import dev.mgcvale.fluidfx.components.groups.VGroup;
import dev.mgcvale.fluidfx.components.layout.Spacers;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginMenu extends BorderGroup {
    private final LoginMenuVM vm;

    public LoginMenu(LoginMenuVM vm) {
        this.vm = vm;
        layControls();
    }

    protected void layControls() {
        Label title = new FLabel("Bem-vindo de volta!").hgrow().wStyleClass("border-pane-region", "header");
        BorderPane.setAlignment(title, Pos.CENTER);

        wStyleClass("form", "border-pane-region").wTop(
            title
        ).wCenter(
            new VGroup().wAlignment(Pos.CENTER).wStyleClass("vbox").wChildren(
                Spacers.vSpacer(),
                new FTextField().wPrompt("E-mail").inMaxWidth(widthProperty().divide(2)).biText(vm.email),
                SpacerFactory.vSpacer(heightProperty().multiply(0.07)),
                new FPasswordField().wPrompt("Senha").inMaxWidth(widthProperty().divide(2)).biText(vm.password),
                Spacers.vSpacer(),
                new FCheckBox("Mantenha-me conectado(a)").hgrow().outSelected(vm.keepConnected),
                new VGroup().wAlignment(Pos.CENTER).wChildren(
                    new FHyperlink().wText("Esqueci minha senha").onAction(e -> vm.onForgotPwd()),
                    new FHyperlink().wText("Não possuo conta").onAction(e -> vm.onCreateAccount())
                ),
                Spacers.vSpacer(),
                new FButton("Login").onAction(e -> vm.doLogin()).applyCustomFunction(HoverAnimator::animateAll)
            )
        );
    }

}

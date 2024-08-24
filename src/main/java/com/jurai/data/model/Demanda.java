package com.jurai.data.model;

import com.jurai.util.EventLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;

import java.util.Map;

public class Demanda implements Model {
    private double id;

    private StringProperty foro = new SimpleStringProperty();
    private StringProperty competencia = new SimpleStringProperty();
    private StringProperty classe = new SimpleStringProperty();
    private StringProperty assuntoPrincipal = new SimpleStringProperty();
    private BooleanProperty pedidoLiminar = new SimpleBooleanProperty(false);
    private BooleanProperty segJustica = new SimpleBooleanProperty(false);
    private DoubleProperty valorAcao = new SimpleDoubleProperty();
    private BooleanProperty dispensaLegal = new SimpleBooleanProperty(false);
    private BooleanProperty justicaGratuita = new SimpleBooleanProperty(false);
    private BooleanProperty guiaCustas = new SimpleBooleanProperty(false);
    private StringProperty resumo = new SimpleStringProperty();
    private StringProperty statusDemanda = new SimpleStringProperty();

    private StringProperty identificacao = new SimpleStringProperty("");

    private ObjectProperty<Requerente> requerente = new SimpleObjectProperty<>();

    public Demanda(String foro,
                   String competencia,
                   String classe,
                   String assuntoPrincipal,
                   Double valorAcao,
                   String resumo,
                   String statusDemanda,
                   Requerente requerente,
                   Map<String, Object> optional) {

        this.foro.set(foro);
        this.competencia.set(competencia);
        this.classe.set(classe);
        this.assuntoPrincipal.set(assuntoPrincipal);
        this.valorAcao.set(valorAcao);
        this.resumo.set(resumo);
        this.statusDemanda.set(statusDemanda);
        this.requerente.set(requerente);

        optional.forEach((key, object) -> {
            try {
                switch (key) {
                    case ("pedidoLiminar"):
                        pedidoLiminar.set((Boolean) object);
                        break;
                    case ("segJustica"):
                        segJustica.set((Boolean) object);
                        break;
                    case ("dispensaLegal"):
                        dispensaLegal.set((Boolean) object);
                        break;
                    case ("justicaGratuira"):
                        justicaGratuita.set((Boolean) object);
                        break;
                    case ("guiaCustas"):
                        guiaCustas.set((Boolean) object);
                        break;
                    case ("identificacao"):
                        identificacao.set((String) object);
                        break;
                    default:
                        break;
                }
            } catch(ClassCastException e) {
                EventLogger.logError("Wrong type in optional field in Demanda constructor");
            }
        });

        // se a identificacao nao for colocada, linkar ela com "Processo de: " + o nome do requerente
        if(identificacao.get().isEmpty()) {
            final StringBinding binding = Bindings.createStringBinding(() ->
                            "Processo de: " + requerente.nomeProperty().get(),
                            requerente.nomeProperty()
            );

            identificacao.bind(binding);
        }
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getForo() {
        return foro.get();
    }

    public StringProperty foroProperty() {
        return foro;
    }

    public void setForo(String foro) {
        this.foro.set(foro);
    }

    public String getCompetencia() {
        return competencia.get();
    }

    public StringProperty competenciaProperty() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia.set(competencia);
    }

    public String getClasse() {
        return classe.get();
    }

    public StringProperty classeProperty() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe.set(classe);
    }

    public String getAssuntoPrincipal() {
        return assuntoPrincipal.get();
    }

    public StringProperty assuntoPrincipalProperty() {
        return assuntoPrincipal;
    }

    public void setAssuntoPrincipal(String assuntoPrincipal) {
        this.assuntoPrincipal.set(assuntoPrincipal);
    }

    public boolean isPedidoLiminar() {
        return pedidoLiminar.get();
    }

    public BooleanProperty pedidoLiminarProperty() {
        return pedidoLiminar;
    }

    public void setPedidoLiminar(boolean pedidoLiminar) {
        this.pedidoLiminar.set(pedidoLiminar);
    }

    public boolean isSegJustica() {
        return segJustica.get();
    }

    public BooleanProperty segJusticaProperty() {
        return segJustica;
    }

    public void setSegJustica(boolean segJustica) {
        this.segJustica.set(segJustica);
    }

    public double getValorAcao() {
        return valorAcao.get();
    }

    public DoubleProperty valorAcaoProperty() {
        return valorAcao;
    }

    public void setValorAcao(double valorAcao) {
        this.valorAcao.set(valorAcao);
    }

    public boolean isDispensaLegal() {
        return dispensaLegal.get();
    }

    public BooleanProperty dispensaLegalProperty() {
        return dispensaLegal;
    }

    public void setDispensaLegal(boolean dispensaLegal) {
        this.dispensaLegal.set(dispensaLegal);
    }

    public boolean isJusticaGratuita() {
        return justicaGratuita.get();
    }

    public BooleanProperty justicaGratuitaProperty() {
        return justicaGratuita;
    }

    public void setJusticaGratuita(boolean justicaGratuita) {
        this.justicaGratuita.set(justicaGratuita);
    }

    public boolean isGuiaCustas() {
        return guiaCustas.get();
    }

    public BooleanProperty guiaCustasProperty() {
        return guiaCustas;
    }

    public void setGuiaCustas(boolean guiaCustas) {
        this.guiaCustas.set(guiaCustas);
    }

    public String getResumo() {
        return resumo.get();
    }

    public StringProperty resumoProperty() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo.set(resumo);
    }

    public String getStatusDemanda() {
        return statusDemanda.get();
    }

    public StringProperty statusDemandaProperty() {
        return statusDemanda;
    }

    public void setStatusDemanda(String statusDemanda) {
        this.statusDemanda.set(statusDemanda);
    }

    public String getNome() {
        return identificacao.get();
    }

    @Override
    public StringProperty nomeProperty() {
        return identificacao;
    }

    public void setNome(String identificacao) {
        this.identificacao.set(identificacao);
    }

    public Requerente getRequerente() {
        return requerente.get();
    }

    public ObjectProperty<Requerente> requerenteProperty() {
        return requerente;
    }

    public void setRequerente(Requerente requerente) {
        this.requerente.set(requerente);
    }
}

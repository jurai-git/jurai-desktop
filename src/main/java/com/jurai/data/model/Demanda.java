package com.jurai.data.model;

import javafx.beans.property.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Demanda implements Model {
    private static final Map<Long, Demanda> registry = new ConcurrentHashMap<>();

    private LongProperty id = new SimpleLongProperty();

    private final StringProperty foro = new SimpleStringProperty();
    private final StringProperty competencia = new SimpleStringProperty();
    private final StringProperty classe = new SimpleStringProperty();
    private final StringProperty assuntoPrincipal = new SimpleStringProperty();
    private final BooleanProperty pedidoLiminar = new SimpleBooleanProperty(false);
    private final BooleanProperty segJustica = new SimpleBooleanProperty(false);
    private final DoubleProperty valorAcao = new SimpleDoubleProperty();
    private final BooleanProperty dispensaLegal = new SimpleBooleanProperty(false);
    private final BooleanProperty justicaGratuita = new SimpleBooleanProperty(false);
    private final BooleanProperty guiaCustas = new SimpleBooleanProperty(false); // TODO: trocar para arquivo
    private final StringProperty resumo = new SimpleStringProperty(); // TODO: texto médio/longo
    private final StringProperty statusDemanda = new SimpleStringProperty();
    private final StringProperty identificacao = new SimpleStringProperty("");
    private final StringProperty dono = new SimpleStringProperty("");

    public Demanda(
                    String foro,
                    String competencia,
                    String classe,
                    String assuntoPrincipal,
                    boolean pedidoLiminar,
                    boolean segJustica,
                    Double valorAcao,
                    boolean dispensaLegal,
                    boolean justicaGratuita,
                    boolean guiaCustas,
                    String resumo,
                    String statusDemanda,
                    String identificacao
    ) {

        this.foro.set(foro);
        this.competencia.set(competencia);
        this.classe.set(classe);
        this.assuntoPrincipal.set(assuntoPrincipal);
        this.pedidoLiminar.set(pedidoLiminar);
        this.segJustica.set(segJustica);
        this.valorAcao.set(valorAcao);
        this.dispensaLegal.set(dispensaLegal);
        this.justicaGratuita.set(justicaGratuita);
        this.guiaCustas.set(guiaCustas);
        this.resumo.set(resumo);
        this.statusDemanda.set(statusDemanda);
        this.identificacao.set(identificacao);
    }

    private Demanda(
            long id,
            String foro,
            String competencia,
            String classe,
            String assuntoPrincipal,
            boolean pedidoLiminar,
            boolean segJustica,
            Double valorAcao,
            boolean dispensaLegal,
            boolean justicaGratuita,
            boolean guiaCustas,
            String resumo,
            String statusDemanda,
            String identificacao,
            String dono
    ) {
        this(foro, competencia, classe, assuntoPrincipal, pedidoLiminar, segJustica, valorAcao, dispensaLegal, justicaGratuita, guiaCustas, resumo, statusDemanda, identificacao);
        this.id.set(id);
        this.dono.set(dono);
    }

    public static Demanda getOrCreate(long id,
                                      String foro,
                                      String competencia,
                                      String classe,
                                      String assuntoPrincipal,
                                      boolean pedidoLiminar,
                                      boolean segJustica,
                                      Double valorAcao,
                                      boolean dispensaLegal,
                                      boolean justicaGratuita,
                                      boolean guiaCustas,
                                      String resumo,
                                      String statusDemanda,
                                      String identificacao,
                                      String dono) {
        return getOrCreate(new Demanda(id, foro, competencia, classe, assuntoPrincipal, pedidoLiminar, segJustica, valorAcao, dispensaLegal, justicaGratuita, guiaCustas, resumo, statusDemanda, identificacao, dono));
    }

    public static Demanda getOrCreate(Demanda d) {
        return registry.compute(d.getId(), (id, existing) -> {
            if (existing == null) return d;

            existing.setNome(d.getNome());
            existing.setClasse(d.getClasse());
            existing.setCompetencia(d.getCompetencia());
            existing.setForo(d.getForo());
            existing.setAssuntoPrincipal(d.getAssuntoPrincipal());
            existing.setPedidoLiminar(d.isPedidoLiminar());
            existing.setSegJustica(d.isSegJustica());
            existing.setValorAcao(d.getValorAcao());
            existing.setDispensaLegal(d.isDispensaLegal());
            existing.setJusticaGratuita(d.isJusticaGratuita());
            existing.setGuiaCustas(d.isGuiaCustas());
            existing.setResumo(d.getResumo());
            existing.setStatusDemanda(d.getStatusDemanda());

            return existing;
        });
    }


    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
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

    public String getDono() {
        return dono.get();
    }

    public void setDono(String dono) {
        this.dono.set(dono);
    }

    public StringProperty donoProperty() {
        return dono;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public StringProperty nomeProperty() {
        return identificacao;
    }

    public void setNome(String identificacao) {
        this.identificacao.set(identificacao);
    }
}

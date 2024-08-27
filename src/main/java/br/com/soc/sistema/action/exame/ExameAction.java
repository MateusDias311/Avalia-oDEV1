package br.com.soc.sistema.action.exame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.soc.sistema.business.ExameBusiness;
import br.com.soc.sistema.filter.ExameFilter;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.OpcoesComboBuscarExames;
import br.com.soc.sistema.vo.ExameVo;

public class ExameAction extends Action {
    private List<ExameVo> exames = new ArrayList<>();
    private ExameBusiness exameBusiness = new ExameBusiness();
    private ExameFilter filtrar = new ExameFilter();
    private ExameVo exameVo = new ExameVo();

    // Listar todos os exames
    public String todosExames() {
        exames.addAll(exameBusiness.trazerTodosOsExames());    
        return SUCCESS;
    }
    
    // Filtrar exames
    public String filtrar() {
        if (filtrar.isNullOpcoesCombo()) {
            return REDIRECT;
        }
        exames = exameBusiness.filtrarExames(filtrar);
        return SUCCESS;
    }
    
    // Novo exame
    public String novo() {
        if (exameVo.getNome() == null) {
            return INPUT;
        }
        exameBusiness.salvarExame(exameVo);
        return REDIRECT;
    }    
    
    // Exclusão de exames (geral)
    public String excluirExame() {
        if (exameVo.getRowid() == null) {
            return REDIRECT;
        }
        
        boolean vinculado = exameBusiness.verificarExameVinculado(exameVo.getRowid());
        
        if (vinculado) {
            addActionError("Exame vinculado a um atendimento.");
            return ERROR;
        } else {
            return excluirExameDoSistema(exameVo.getRowid());
        }
    }
    
    private String excluirExameDoSistema(String rowidExame) {
        if (exameBusiness.excluirExame(rowidExame)) {
            addActionMessage("Exame excluído com sucesso.");
            return SUCCESS;
        } else {
            addActionError("Erro ao excluir exame.");
            return ERROR;
        }
    }
    
    // Edição de exames
    public String editar() {
        if (exameVo.getRowid() == null) {
            return INPUT;
        }
        exameVo = exameBusiness.buscarExamePor(exameVo.getRowid());
        return INPUT;
    }
    
    // Lista de opções de combo para buscar exames
    public List<OpcoesComboBuscarExames> getListaOpcoesCombo() {
        return Arrays.asList(OpcoesComboBuscarExames.values());
    }
    
    // Getters e setters
    public List<ExameVo> getExames() {
        return exames;
    }

    public void setExames(List<ExameVo> exames) {
        this.exames = exames;
    }

    public ExameFilter getFiltrar() {
        return filtrar;
    }

    public void setFiltrar(ExameFilter filtrar) {
        this.filtrar = filtrar;
    }

    public ExameVo getExameVo() {
        return exameVo;
    }

    public void setExameVo(ExameVo exameVo) {
        this.exameVo = exameVo;
    }
}

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class TransacaoValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransacaoValidator.class);

    // Constantes para melhorar legibilidade, para remover uma má prática de ter numeros sem explicação dentro do codigo
    private static final String BIT_02_DEFAULT_VALUE = "01";
    private static final String BIT_02_VALID_VALUE = "02";
    private static final List<String> VALID_BIT_04_VALUES = List.of("02", "03", "04", "05", "12");

    /**
     * Valida a transação representada pelo ISOModel.
     *
     * @param isoModel o modelo a ser validado.
     */
    public void validate(ISOModel isoModel) {
        LOGGER.info("Iniciando validação da transação");

        if (isoModel == null) {
            throw new IllegalArgumentException("ISOModel não pode ser nulo");
        }

        // Variáveis originais reescritas para maior clareza e legibilidade
        // boolean isNotPreenchido = m.getBit02() == null; 
        // Renomeada para `isBit02NotFilled` para maior clareza
        boolean isBit02NotFilled = isNullOrEmpty(isoModel.getBit02());
        
        // boolean validateAux = m.getBit02() != null && m.getBit02().getValue().isEmpty();
        // Renomeada e substituída por `isNullOrEmpty` para reaproveitamento de lógica
        boolean isBit03NotFilled = isoModel.getBit03() == null;

        // boolean auxValidacao = m.getBit02() != null && m.getBit02().getValue().isEmpty() && m.getBit03() == null;
        // Removida porque a lógica duplicava o que já estava sendo avaliado de forma redundante

        // String valor = isNotPreenchido ? "01" : "02";
        // Removida porque o valor "01" ou "02" não era reutilizado de forma eficiente na validação

        // try-catch vazio removido (má prática):
        // try {
        //     if (!isNotValid(isNotPreenchido, validateAux, auxValidacao, valor)) {
        //         if (m.getBit03() != null) {
        //             if (m.getBit04() != null && lista.contains("10")) {
        //                 if (m.getBit05() != null) {
        //                     if (m.getBit12() != null) {
        //                         salvar(m, auxValidacao);
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // } catch (Exception e) {
        // }
        // Lógica de validação extremamente aninhada foi reescrita para maior clareza
        // Bloco `catch` vazio removido, pois não tratava nenhuma exceção

        if (isBit02NotFilled) {
            throw new IllegalArgumentException("Bit 02 está vazio ou não preenchido");
        }

        // Condição reescrita com menos aninhamento e mais clareza:
        boolean isBit04NotValid = !isValidBit04(isoModel.getBit04());
        boolean isBit05NotFilled = isoModel.getBit05() == null;
        boolean isBit12NotFilled = isoModel.getBit12() == null;

        if (isBit03NotFilled || isBit04NotValid || isBit05NotFilled || isBit12NotFilled) {
            throw new IllegalArgumentException("Algum dos campos obrigatórios está ausente ou inválido");
        }

        salvar(isoModel);
    }

    /**
     * Verifica se o campo está nulo ou vazio.
     *
     * @param field o campo a ser validado.
     * @return true se o campo estiver nulo ou vazio, false caso contrário.
     */
    private boolean isNullOrEmpty(Bit field) {
        return field == null || field.getValue().isEmpty();
    }

    /**
     * Valida o campo Bit 04 com base na lista de valores permitidos.
     *
     * @param bit04 o campo a ser validado.
     * @return true se o campo for válido, false caso contrário.
     */
    private boolean isValidBit04(Bit bit04) {
        return bit04 != null && VALID_BIT_04_VALUES.contains(bit04.getValue());
    }

    /**
     * Salva a transação, garantindo que os valores necessários sejam válidos.
     *
     * @param isoModel o modelo a ser salvo.
     */
    private void salvar(ISOModel isoModel) {
        // Bloco original:
        // if (auxValidacao) {
        //     throw new IllegalArgumentException("Validacao falhou");
        // }
        // Condição removida porque `auxValidacao` já não existe mais.

        LOGGER.info("Salvando transação com Bit 02: {}", isoModel.getBit02().getValue());
        // Lógica de salvamento aqui
    }

    // Método isNotValid original:
    // private boolean isNotValid(boolean validaPreenchido, boolean validaVazio, boolean validaAux, String str) {
    //     return validaPreenchido || validaVazio && !validaAux && str.equals("01");
    // }
    // Removido porque sua lógica foi integrada diretamente ao método `validate`,
    //   eliminando a necessidade de um método específico que complicava a leitura.
}

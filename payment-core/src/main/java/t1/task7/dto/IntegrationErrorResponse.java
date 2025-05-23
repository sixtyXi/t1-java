package t1.task7.dto;

public record IntegrationErrorResponse(
        String reason,
        String errorMessage,
        int errorCode
) {
    public IntegrationErrorResponse(String errorMessage, int errorCode) {
        this("Получена ошибка от вызываемого сервиса", errorMessage, errorCode);
    }
}

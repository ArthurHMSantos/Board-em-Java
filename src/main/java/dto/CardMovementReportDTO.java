package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
public class CardMovementReportDTO {
    private Long cardId;
    private String cardTitle;
    private String columnName;
    private OffsetDateTime enteredAt;
    private OffsetDateTime movedAt;
}
package service;

import dto.CardMovementReportDTO;
import persistence.dao.CardDAO;

import java.sql.SQLException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class CardReportService {

    private final CardDAO cardDAO;

    public CardReportService(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    public void generateBoardReport(Long boardId) throws SQLException {
        List<CardMovementReportDTO> cardReports = cardDAO.getCardMovementsByBoard(boardId);

        System.out.println("Relatório do Board:");
        System.out.println("=====================================");
        System.out.printf("%-10s | %-20s | %-15s | %-15s | %-15s%n",
                "Card ID", "Título", "Coluna", "Entrou em", "Tempo na coluna");

        for (CardMovementReportDTO report : cardReports) {
            var enteredAt = report.getEnteredAt();
            var movedAt = report.getMovedAt() != null ? report.getMovedAt() : OffsetDateTime.now();

            var duration = Duration.between(enteredAt, movedAt);
            var hours = duration.toHoursPart();
            var minutes = duration.toMinutesPart();

            System.out.printf("%-10d | %-20s | %-15s | %-15s | %02dh %02dm%n",
                    report.getCardId(),
                    report.getCardTitle(),
                    report.getColumnName(),
                    enteredAt,
                    hours, minutes);
        }

        System.out.println("=====================================");
    }
}
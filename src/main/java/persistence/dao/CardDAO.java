package persistence.dao;

import dto.CardDetailsDTO;
import dto.CardMovementReportDTO;
import persistence.entity.CardEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class CardDAO {

    private Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        var sql = "INSERT INTO CARDS (title, description, board_column_id, entered_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP);";
        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i++, entity.getBoardColumn().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }


    public void moveToColumn(final Long columnId, final Long cardId) throws SQLException {
        var sql = "UPDATE CARDS SET board_column_id = ?, moved_at = CURRENT_TIMESTAMP WHERE id = ?;";
        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setLong(i++, columnId);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }


    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        var sql =
                """
                SELECT c.id,
                       c.title,
                       c.description,
                       b.blocked_at,
                       b.block_reason,
                       c.board_column_id,
                       bc.name,
                       c.entered_at,    -- Recuperar date de entrada
                       c.moved_at,      -- Recuperar date de movimentação
                       (SELECT COUNT(sub_b.id)
                           FROM BLOCKS sub_b
                          WHERE sub_b.card_id = c.id) blocks_amount
                  FROM CARDS c
                  LEFT JOIN BLOCKS b
                    ON c.id = b.card_id
                   AND b.unblocked_at IS NULL
                 INNER JOIN BOARDS_COLUMNS bc
                    ON bc.id = c.board_column_id
                  WHERE c.id = ?;
                """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                var dto = new CardDetailsDTO(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        nonNull(resultSet.getString("b.block_reason")),
                        toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("c.board_column_id"),
                        resultSet.getString("bc.name"),
                        toOffsetDateTime(resultSet.getTimestamp("c.entered_at")), // Mapear entered_at
                        toOffsetDateTime(resultSet.getTimestamp("c.moved_at"))    // Mapear moved_at
                );
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }


    public List<CardMovementReportDTO> getCardMovementsByBoard(final Long boardId) throws SQLException {
        var sql = """
        SELECT c.id AS card_id,
               c.title AS card_title,
               c.entered_at AS column_entered_at,
               c.moved_at AS column_moved_at,
               bc.name AS column_name,
               b.id AS board_id
          FROM CARDS c
         INNER JOIN BOARDS_COLUMNS bc
            ON c.board_column_id = bc.id
         INNER JOIN BOARDS b
            ON bc.board_id = b.id
         WHERE b.id = ?
         ORDER BY c.id, c.entered_at;
    """;

        var cardMovementReports = new ArrayList<CardMovementReportDTO>();

        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var cardMovement = new CardMovementReportDTO(
                        resultSet.getLong("card_id"),
                        resultSet.getString("card_title"),
                        resultSet.getString("column_name"),
                        toOffsetDateTime(resultSet.getTimestamp("column_entered_at")),
                        toOffsetDateTime(resultSet.getTimestamp("column_moved_at"))
                );
                cardMovementReports.add(cardMovement);
            }
        }

        return cardMovementReports;
    }
}
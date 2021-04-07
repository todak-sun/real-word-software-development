package io.todak.realworldsoftware.chapter06.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Extractor<R> {

    R run(PreparedStatement statement) throws SQLException;

}

package me.danbrown.railflow.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static uk.co.railflow.generated.Tables.FILE_IMPORT;

@Repository
public class FileImportRepository {

    private DSLContext db;

    public FileImportRepository(DSLContext db) {
        this.db = db;
    }

    public boolean importedFileExists(String etag) {
        return db.fetchExists(db.selectFrom(FILE_IMPORT).where(FILE_IMPORT.ETAG.eq(etag)));
    }

    public void insertImportedFile(String etag) {
        db.insertInto(FILE_IMPORT)
                .set(FILE_IMPORT.ETAG, etag)
                .set(FILE_IMPORT.IMPORT_DATE, LocalDateTime.now())
                .execute();
    }
}

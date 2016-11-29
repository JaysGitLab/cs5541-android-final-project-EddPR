package com.bignerdranch.android.projectmanager.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.projectmanager.Crime;
import com.bignerdranch.android.projectmanager.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Eduardo on 10/2/2016.
 */
public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        String subtitle = getString(getColumnIndex(CrimeTable.Cols.SUBTITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        long contactId = getLong(getColumnIndex(CrimeTable.Cols.CONTACT_ID));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setSubtitle(subtitle);
        crime.setDate(new Date(date));
        crime.setFinished(isSolved != 0);
        crime.setAssignee(suspect);
        crime.setContactId(contactId);

        return crime;
    }
}

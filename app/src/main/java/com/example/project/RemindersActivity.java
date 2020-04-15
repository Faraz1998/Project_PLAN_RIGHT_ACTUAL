package com.example.project;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.Notes_file.Notes;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class RemindersActivity extends AppCompatActivity {
    private ListView mListView;
    private RemindersDbAdapter mDbAdapter;
    private RemindersSimpleCursorAdapter mCursorAdapter;



        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reminders);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.Reminder);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.Reminder: return true;

                        case R.id.account:
                            startActivity(new Intent(getApplicationContext(), Account.class));
                            return true;

                            case R.id.Notes:
                                startActivity(new Intent(getApplicationContext(), Notes.class));
                                overridePendingTransition(0, 0);
                                return true;
                                case R.id.home:
                                    startActivity(new Intent(getApplicationContext(), Home.class));
                                    overridePendingTransition(0, 0);
                                    return true;
                    }
                    return false;
                }
            });
            mListView = (ListView) findViewById(R.id.reminders_list_view);
            mListView.setDivider(null);
            mDbAdapter = new RemindersDbAdapter(this);
            mDbAdapter.open();
            if (savedInstanceState == null) {
                mDbAdapter.deleteAllReminders(); //Clear set reminders
            }


            Cursor cursor = mDbAdapter.fetchAllReminders();
            //from columns defined in the db
            String[] from = new String[]{
                    RemindersDbAdapter.COL_CONTENT};
            //to the ids of views in the layout
            int[] to = new int[]{R.id.row_text};

            mCursorAdapter = new RemindersSimpleCursorAdapter(
                    RemindersActivity.this,

                    R.layout.rowreminders,   //layout of reminders, coming from the 'rowreminders.xml' file
                    //cursor
                    cursor,
                    //columns defined in the db to the ids of views in the layout
                    from, to, 0);
            //listview will update
            mListView.setAdapter(mCursorAdapter);

            //clicking on one reminder
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RemindersActivity.this);
                    ListView modeListView = new ListView(RemindersActivity.this);
                    String[] modes = new String[]{"Edit Reminder", "Delete Reminder"};
                    ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(RemindersActivity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                    modeListView.setAdapter(modeAdapter);
                    builder.setView(modeListView);
                    final Dialog dialog = builder.create();
                    dialog.show();
                    modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //edit reminder
                            if (position == 0) {
                                int nId = getIdFromPosition(masterListPosition);
                                Reminder reminder = mDbAdapter.fetchReminderById(nId);
                                fireCustomDialog(reminder);
                                //delete reminder
                            } else {
                                mDbAdapter.deleteReminderById(getIdFromPosition(masterListPosition));
                                mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                            }
                            dialog.dismiss();
                        }
                    });
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean
                            checked) {
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        MenuInflater inflater = mode.getMenuInflater();
                        inflater.inflate(R.menu.cam_menu, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_delete_reminder:
                                for (int nC = mCursorAdapter.getCount() - 1; nC >= 0; nC--) {
                                    if (mListView.isItemChecked(nC)) {
                                        mDbAdapter.deleteReminderById(getIdFromPosition(nC));
                                    }
                                }
                                mode.finish();
                                mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                                return true;
                        }
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                    }
                });
            }
        }

        private int getIdFromPosition ( int nC){
            return (int) mCursorAdapter.getItemId(nC);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private void fireCustomDialog ( final Reminder reminder){
            // custom dialog
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.customdialog);
            TextView titleView = dialog.findViewById(R.id.custom_title);
            final EditText editCustom =  dialog.findViewById(R.id.custom_edit_reminder);
            Button commitButton = dialog.findViewById(R.id.custom_button_commit);
            final CheckBox checkBox =  dialog.findViewById(R.id.custom_check_box);
            LinearLayout rootLayout =  dialog.findViewById(R.id.custom_root_layout);
            final boolean isEditOperation = (reminder != null);
            //editing a reminder
            if (isEditOperation) {
                titleView.setText("Edit Reminder");
                checkBox.setChecked(reminder.getImportant() == 1);
                editCustom.setText(reminder.getContent());
                rootLayout.setBackgroundColor(getResources().getColor(R.color.blue));
            }
            commitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String reminderText = editCustom.getText().toString();
                    if (isEditOperation) {
                        Reminder reminderEdited = new Reminder(reminder.getId(),
                                reminderText, checkBox.isChecked() ? 1 : 0);
                        mDbAdapter.updateReminder(reminderEdited);
                        //new reminder
                    } else {
                        mDbAdapter.createReminder(reminderText, checkBox.isChecked());
                    }
                    mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                    dialog.dismiss();
                }
            });
            Button buttonCancel =  dialog.findViewById(R.id.custom_button_cancel);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // adds items to the action bar
            getMenuInflater().inflate(R.menu.reminders, menu);
            return true;
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.action_new:
                    //add new Reminder
                    fireCustomDialog(null);
                    return true;
                default:
                    return false;
            }
        }
    }



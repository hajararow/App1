package com.example.app1;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import androidx.annotation.NonNull;
import android.util.Log;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainJobSeeker extends AppCompatActivity {

    Spinner spinnerAge;
    Spinner spinnerRegion;
    Spinner spinnerCity;
    Spinner spinnerJobType;
    Spinner spinnerJobField;
    Spinner spinnerSpecificJob;
    Button buttonSearch;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Job_search) {
                Intent intent = new Intent(this, MainJobSeeker.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.Favorite_jobs) {
            Intent intent = new Intent(this, SearchResultsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.LogOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_job_seeker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        spinnerAge = findViewById(R.id.spinner_age);
        spinnerRegion = findViewById(R.id.spinner_region);
        spinnerCity = findViewById(R.id.spinner_city);
        spinnerJobType = findViewById(R.id.spinner_job_type);
        spinnerJobField = findViewById(R.id.spinner_job_field);
        spinnerSpecificJob = findViewById(R.id.spinner_specific_job);

        buttonSearch = findViewById(R.id.button_search);

        String[] ageOptions = {"מעל גיל 18", "מעל גיל 21", "ללא הגבלת גיל"};
        String[] regionOptions = {"אזור צפון", "אזור מרכז ושרון", "אזור הדרום" , "אזור ירושלים"};
        String[] cityOptions = {"חיפה", "תל אביב", "באר שבע", "ירושלים"};
        String[] jobTypeOptions = {"משרה מלאה", "משרה חלקית", "עבודה זמנית"};
        String[] jobFieldOptions = {"אבטחת מידע", "אדמיניסטרציה", "אופנה", "אינטרנט", "ביטוח", "בכירים / ניהול", "בנייה ונדלן", "בעלי מקצוע", "הדרכה / הוראה", "הייטק - QA", "הייטק - חומרה", "הייטק - כללי", "הייטק - תוכנה", "הנדסה", "יופי, טיפוח וספא", "כללי", "כספים / שוק ההון", "לוגיסטיקה / שילוח", "מדעים / ביוטק", "מכירות", "מלונאות / מסעדנות", "משאבי אנוש", "עיצוב", "עריכת דין", "פרסום / מדיה / תקשורת", "קמעונאות", "רכב / תחבורה", "רפואה / בריאות", "שיווק", "שירות לקוחות", "שמירה / אבטחה", "תיירות / תעופה", "תעשיה / ייצור"};
        String[] specificJobOptions = {"אינטגרטור/ית", "איש/ת מערכות מידע", "איש/ת IT", "איש/ת NOC"};


        setUpSpinner(spinnerAge, ageOptions);
        setUpSpinner(spinnerRegion, regionOptions);
        setUpSpinner(spinnerCity, cityOptions);
        setUpSpinner(spinnerJobType, jobTypeOptions);
        setUpSpinner(spinnerJobField, jobFieldOptions);
        setUpSpinner(spinnerSpecificJob, specificJobOptions);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        MainJobSeeker.this,
                        SearchResultsActivity.class
                );

                startActivity(intent);
            }
        });

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedRegion = spinnerRegion.getSelectedItem().toString();

                String[] cityOptions;

                switch (selectedRegion) {

                    case "אזור צפון":
                        cityOptions = new String[]{
                                "בית שאן", "דליה", "דלית אל-כרמל", "חיפה", "טבריה", "טירת כרמל", "כרמיאל", "מג'דל כרום", "מעלות תרשיחא", "נהריה", "נווה יוסף", "נצרת", "נשר", "סכנין", "עילוט", "עכו", "עפולה", "קריית אליעזר", "קריית אתא", "קריית ביאליק", "קריית חיים", "קריית ים", "קריית מוצקין", "קריית שמונה", "שפרעם"};
                        break;

                    case "אזור מרכז ושרון":
                        cityOptions = new String[]{
                                "אור עקיבא", "בני ברק", "בת ים", "גבעתיים", "הוד השרון", "הרצליה", "זכרון יעקב", "חדרה", "חולון", "כפר סבא", "לוד", "נתניה", "פרדס חנה - כרכור", "פתח תקווה", "ראשון לציון", "רחובות", "רמלה", "רמת גן", "רעננה", "תל אביב - יפו"};
                        break;

                    case "אזור הדרום":
                        cityOptions = new String[]{
                                "אופקים", "אילת", "אשדוד", "אשקלון", "באר שבע", "דימונה", "ירוחם", "נתיבות", "ערד", "שדרות"};
                        break;

                    case "אזור ירושלים":
                        cityOptions = new String[]{
                                "אלקנה", "אריאל", "בית אל", "בית שמש", "ביתר עילית", "גבעת זאב", "גוש עציון", "ירושלים", "מבשרת ציון", "מודיעין עילית", "מעלה אדומים", "קדומים", "קרני שומרון"};
                        break;

                    default:
                        cityOptions = new String[]{};
                }

                setUpSpinner(spinnerCity, cityOptions);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerJobField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedField = spinnerJobField.getSelectedItem().toString();

                String[] specificJobs;

                switch (selectedField) {

                    case "אבטחת מידע":
                        specificJobs = new String[]{"אינטגרטור/ית", "איש/ת מערכות מידע", "איש/ת IT", "איש/ת NOC", "אנליסט/ית אבטחת מידע", "אנליסט/ית סייבר", "חוקר/ת חולשות", "חוקר/ת נוזקות", "מומחה/ית אבטחת מידע", "מומחה/ית תקיפה Penetration Test", "מיישם/ת", "מנהל/ת אבטחת מידע", "מנהל/ת מערכות מידע", "מנהל/ת פרויקטים", "מפעיל/ת SOC", "משרות הייטק כללי", "תקשורת / שו\"ב", "תשתיות", "Help Desk / תמיכה", "sys admin win"};
                        break;

                    case "אדמיניסטרציה":
                        specificJobs = new String[]{"גבביה", "מזכיר/ה בכיר/ה", "מזכיר/ה משפטי/ת", "מזכיר/ה עברית", "מזכיר/ה רפואי/ת", "מנהל/ת אדמיניסטרטיבי/ת", "מנהל/ת משרד", "משרות כלליות", "נציג/ה פרונטלי/ת", "סטודנט/ית", "עוזר/ת אישי/ת", "פקיד/ה", "פקיד/ת קבלה", "רכז/ת אדמיניסטרטיבי/ת", "שירות לקוחות", "Back Office - אדמיניסטרציה", "Back Office - כספים", "Back Office - מכירות", "Back Office - שירות לקוחות"};
                        break;

                    case "אופנה":
                        specificJobs = new String[]{"אחראי/ת משמרת", "איש/ת מכירות", "הנדסאי/ת מכונות", "מוכר/ת", "מוכר/ת אופנה", "מחסנאי/ת", "מכונאי/ת רכב", "מלקט/ת", "מנהל/ת אופנה", "מנהל/ת מכירות", "מנהל/ת סניף", "מנהל/ת צוות", "מעצב/ת אופנה", "מעצב/ת מסחרי/ת / VM", "משרות כלליות", "נציג/ת מכירות", "נציג/ת מכירות פרונטליות", "סטודנט/ית", "קניין/ית אופנה"};
                        break;

                    case "אינטרנט":
                        specificJobs = new String[]{"איש/ת מכירות", "איש/ת שיווק", "אנליסט/ית שיווק", "כותב/ת תוכן", "מומחה/ית SEM/PPC", "מנהל/ת אתר", "מנהל/ת מדיה", "מנהל/ת מוצר", "מנהל/ת מוצר - אינטרנט", "מנהל/ת סושיאל מדיה", "מנהל/ת פרויקטים באינטרנט", "מנהל/ת פרויקטים בשיווק", "מנהל/ת שיווק", "מנהל/ת שיווק דיגיטלי", "מפתח/ת Web", "סחר אלקטרוני", "עורך/ת / מנהל/ת תוכן", "קריאייטיב", "Full Stack", "Web Marketing"};
                        break;

                    case "ביטוח":
                        specificJobs = new String[]{"איש/ת מכירות", "איש/ת מכירות ביטוח", "ביטוח - ניהול", "חתם/ת", "יועץ/ת פנסיוני/ת", "מוקדן/ית", "מנהל/ת תיקי לקוחות", "מסלק/ת תביעות", "נציג/ת טלמרקטינג", "נציג/ת מכירות", "נציג/ת שירות לקוחות - ביטוח", "סוכן/ת ביטוח", "פקיד/ה", "פקיד/ת ביטוח", "רפרנט/ית ביטוח", "שירות לקוחות", "Back office - אדמיניסטרציה", "Back office - ביטוח", "Back office - כספים", "Back office - שירות לקוחות"};
                        break;

                    case "בכירים / ניהול":
                        specificJobs = new String[]{"איש/ת שיווק", "בכירים כללי", "לוגיסטיקה - ניהול", "מומחה/ית אבטחת מידע", "מנהל/ת אבטחת מידע", "מנהל/ת מותג", "מנהל/ת מכירות", "מנהל/ת נכסים", "מנהל/ת סחר", "מנהל/ת פיתוח עסקי", "מנהל/ת פרויקטים בשיווק", "מנהל/ת רכש", "מנהל/ת שיווק", "מנהל/ת שיווק דיגיטלי", "מנהל/ת תפעול", "מנכ\"ל/ית", "סמנ\"ל/ית מכירות", "סמנכ\"ל/ית רכש ולוגיסטיקה", "סמנכ\"ל/ית תפעול"};
                        break;

                    case "בנייה ונדל\"ן":
                        specificJobs = new String[]{"אדריכל/ית", "מהנדס/ת בניין", "מנהל/ת פרויקט בנייה", "מפקח/ת בנייה", "מודד/ת מוסמך/ת", "קונסטרוקטור/ית", "שרטט/ת אדריכלות", "מהנדס/ת ביצוע", "מנהל/ת עבודה בבנייה", "עוזר/ת מנהל עבודה", "חשמלאי/ת בניין", "אינסטלטור/ית בניין", "טכנאי/ת מעליות", "טפסן/ית", "ברזלן/ית", "רצף/ת", "טייח/ת", "שיפוצניק/ית", "מתווך/ת נדל״ן", "יועץ/ת נדל״ן"};
                        break;

                    case "בעלי מקצוע":
                        specificJobs = new String[]{"אחזקה", "אינסטלציה", "חייל/ת משוחרר/ת", "חשמל ואלקטרוניקה", "חשמלאי", "טכנאי/ת", "טכנאי/ת - תעשייה וייצור", "טכנאי/ת מיזוג אוויר", "מכונאי/ת", "מכונאי/ת רכב", "מנהל/ת אחזקה", "מנהל/ת עבודה", "מפעיל/ת מכונות", "מרכיב/ה מכאני/ת", "משרות כלליות", "מתקינים", "עובד/ת ייצור", "רתכים / מסגרים", "תחזוקה"};
                        break;

                    case "הדרכה / הוראה":
                        specificJobs = new String[]{"אח/ות", "אקדמאי/ת", "גנן/ת", "גרונטולוג/ית", "הדרכה", "חינוך מיוחד", "יועץ/ת / פסיכולוג/ית", "מדריך/ת ספורט", "מורה", "מטפל/ת / שמרטף/ית", "מנחה/ת קבוצות", "משרות כלליות", "ניהול", "סטודנט/ית", "סייע/ת", "עובד/ת סוציאלי/ת", "רכז/ת אדמיניסטרטיבי/ת", "רכז/ת הדרכה/הוראה", "Back office - אדמיניסטרציה"};
                        break;

                    case "הייטק - QA":
                        specificJobs = new String[]{"אבטחת איכות", "אינטגרטור/ית", "איש/ת / הנדסאי/ת QA", "בדיקות אוטומטיות", "בדיקות ידניות", "בודק/ת ERP / CRM", "ביוטכנולוגיה", "בקרת איכות", "מהנדס/ת איכות", "מהנדס/ת מכונות", "מהנדס/ת תוכנה", "מהנדס/ת QA", "מנהל/ת איכות", "מנהל/ת הבטחת איכות", "מנהל/ת QA", "מפתח/ת כלי בדיקות", "משרות הייטק כללי", "סטודנטים - QA", "ראש צוות QA", "Python"};
                        break;

                    case "הייטק - חומרה":
                        specificJobs = new String[]{"איש/ת מערכות מידע", "איש/ת IT", "הדרכה / הטמעה", "הנדסאי/ת אלקטרוניקה", "הנדסאי/ת חשמל", "הנדסאים כללי", "טכנאי/ת", "טכנאי/ת מחשבים", "מהנדס/ת אלקטרוניקה", "מהנדס/ת חשמל", "מהנדס/ת מערכת", "מומחה/ית אבטחת מידע", "מנהל/ת פרויקטים", "מנהל/ת רשת", "מנתח/ת מערכות", "משרות הייטק כללי", "תשתיות", "Help Desk / תמיכה", "sys admin unix", "sys admin win"};
                        break;

                    case "הייטק - כללי":
                        specificJobs = new String[]{"איש/ת / הנדסאי/ת QA", "איש/ת מערכות מידע", "איש/ת IT", "הדרכה / הטמעה", "טכנאי/ת מחשבים", "מומחה/ית אבטחת מידע", "מיישם/ת", "מנהל/ת פרויקטים", "מנהל/ת רשת", "מנתח/ת מערכות", "משרות הייטק כללי", "שירות לקוחות", "תמיכה טכנית", "תשתיות", "BI", "Cloud", "Data Analyst", "DevOps", "Help Desk / תמיכה", "sys admin wi"};
                        break;

                    case "הייטק - תוכנה":
                        specificJobs = new String[]{"מהנדס/ת תוכנה", "מפתח/ת Web", "משרות הייטק כללי", "מתכנת/ת", "ראש צוות פיתוח", "Backend", "BI", "Big Data", "C#", "C++ / C", "Cloud", "Data Analyst", "Data Engineer", "DevOps", "ETL", "Full Stack", "JAVA", "NET.", "PL \\ SQL", "Python"};
                        break;

                    case "הנדסה":
                        specificJobs = new String[]{"אדריכל/ית", "הנדסאי/ת אלקטרוניקה", "הנדסאי/ת בניין", "הנדסאי/ת חשמל", "הנדסאי/ת מכונות", "הנדסאי/ת תעשייה וניהול", "הנדסאים כללי", "הנדסה - ניהול", "חשמלאי", "טכנאי/ת", "מהנדס/ת אזרחי/ת", "מהנדס/ת אלקטרוניקה", "מהנדס/ת בניין", "מהנדס/ת חשמל", "מהנדס/ת מכונות", "מהנדס/ת תעשייה וניהול", "מהנדסים כללי", "מנהל/ת פרויקטים", "מנהל/ת פרויקטים בנייה", "תפ\"י"};
                        break;

                    case "יופי, טיפוח וספא":
                        specificJobs = new String[]{"מאפר/ת מקצועי/ת", "מעצב/ת שיער", "קוסמטיקאי/ת", "טכנאי/ת מניקור/פדיקור", "מדריך/ת ספא", "מנהל/ת ספא", "עיסוי רפואי / טיפולי", "עיסוי שוודי", "מטפל/ת רפלקסולוגי", "מאמן/ת יוגה", "מדריך/ת פילאטיס", "אסתטיקאי/ת", "מאמן/ת כושר אישי", "קוסמטיקאי/ת רפואי/ת", "מטפל/ת בתזונה ובריאות", "יועץ/ת יופי", "מדריך/ת טיפולי גוף", "מתרגל/ת מדיטציה", "סטייליסט/ית", "עובד/ת בסלון יופי"};
                        break;

                    case "כללי":
                        specificJobs = new String[]{"אחראי/ת משמרת", "איש/ת מכירות", "לוגיסטיקה כללי", "מאבטח/ת", "מוכר/ת", "מוקדן/ית", "מחסנאי/ת", "מלקט/ת", "משרות כלליות", "נהג/ת", "נהג/ת ג'", "נהג/ת חלוקה", "נציג/ת טלמרקטינג", "נציג/ת מכירות", "סטודנט/ית", "סייר/ת", "עבודה מועדפת", "שומר/ת", "שירות לקוחות"};
                        break;

                    case "כספים / שוק ההון":
                        specificJobs = new String[]{"אנליסט/ית", "בנקאות כללי", "בקרה תקציבית", "גבייה", "חשב/ת", "חשב/ת שכר", "יועץ/ת משכנתאות", "כלכלן/ית", "מנהל/ת השקעות / תיקים","מנהל/ת חשבונות", "מנהל/ת כספים", "מתמחה - כספים", "סטודנט/ית - כספים", "קניין/ית", "רואה/ת חשבון", "רכש", "רפרנט/ית אשראי", "שירות לקוחות", "Back office - אדמיניסטרציה", "Back Office - כספים"};
                        break;

                    case "לוגיסטיקה / שילוח":
                        specificJobs = new String[]{"יבוא / יצוא", "לוגיסטיקה - ניהול", "לוגיסטיקה כללי", "מחסנאי/ת", "מחסנאי/ת ממוחשב/ת", "מלגזן/ית", "מלקט/ת", "מנהל/ת מחסן", "משרות כלליות", "נהג/ת", "נהג/ת ג'", "נהג/ת חלוקה", "נהג/ת מעל 15 טון", "פקיד/ת מחסן", "קניין/ית", "רכב כללי", "רכש", "תפ\"י", "תפעול"};
                        break;

                    case "מדעים / ביוטק":
                        specificJobs = new String[]{"אבטחת איכות", "אח/ות", "איכות הסביבה / קיימות", "ביוטכנולוגיה", "ביולוגיה", "בקרת איכות", "הנדסאי/ת אלקטרוניקה", "חוקר/ת", "טכנאי/ת", "טכנאי/ת - תעשייה וייצור", "כימיה", "לבורנט/ית", "מהנדס/ת חומרים", "מהנדס/ת כימיה", "מנהל/ת מעבדה", "משרות כלליות", "סטודנטים - מדעים / ביוטק", "רגולציה", "רוקח/ת", "רפואה - כללי"};
                        break;

                    case "מכירות":
                        specificJobs = new String[]{"אחראי/ת משמרת", "איש/ת מכירות", "מוכר/ת", "מוקדן/ית", "מנהל/ת מוקד", "מנהל/ת מכירות", "מנהל/ת סניף", "מנהל/ת צוות", "מנהל/ת תיקי לקוחות", "משרות כלליות", "מתאם/ת פגישות", "נציג/ת טלמרקטינג", "נציג/ת מכירות", "נציג/ת מכירות פרונטליות", "סוכן/ת מכירות", "סוכן/ת מכירות שטח", "סטודנט/ית", "שירות לקוחות", "Back office - מכירות"};
                        break;

                    case "מלונאות / מסעדנות":
                        specificJobs = new String[]{"אופרציה", "טבח/ית", "מזכיר/ה עברית", "מזכיר/ה רפואי/ת", "מלונאות כללי", "מלצר/ית / ברמן/ית / בריסטה", "מנהל/ת / אחראי/ת משמרת", "מנהל/ת מטבח", "מנהל/ת משרד", "מסעדנות - ניהול", "משרות כלליות", "סטודנט/ית", "עובד/ת דלפק", "עובד/ת מטבח", "פקיד/ה", "פקיד/ת קבלה", "רכז/ת אדמיניסטרטיבי/ת", "שירות לקוחות", "Back office - אדמיניסטרציה"};
                        break;

                    case "משאבי אנוש":
                        specificJobs = new String[]{"הדרכה", "יועץ/ת ארגוני/ת", "מאבחן/ת", "מנהל/ת אדמיניסטרטיבי/ת", "מנהל/ת גיוס", "מנהל/ת משאבי אנוש", "מנהל/ת משרד", "מנהל/ת תיקי לקוחות", "מראיין/ת", "סדרן/ית", "פיתוח ארגוני", "רווחה", "רכז/ת אדמיניסטרטיבי/ת", "רכז/ת גיוס", "רכז/ת גיוס טכנולוגי", "רכז/ת משאבי אנוש", "רכז/ת סורסינג", "שכר והטבות", "Back office - אדמיניסטרציה", "HRBP"};
                        break;

                    case "עיצוב":
                        specificJobs = new String[]{"אדריכל/ית", "איש/ת מכירות", "ביצועיסט/ית", "הדרכה", "הנדסאי/ת אדריכלות", "הנדסאי/ת בניין", "מוכר/ת אופנה", "מנהל/ת אופנה", "מעצב/ת אופנה", "מעצב/ת אתרים", "מעצב/ת גרפי/ת", "מעצב/ת מטבחים", "מעצב/ת מסחרי/ת / VM", "מעצב/ת פנים", "מעצב/ת תעשייתי/ת", "עורך/ת וידאו / אפטריסט/ית", "קניין/ית אופנה", "קריאייטיב", "שרטט/ת", "תופר/ת / תדמיתן/ית"};
                        break;

                    case "עריכת דין":
                        specificJobs = new String[]{"אדריכל/ית", "גבייה", "הוצאה לפועל", "יועץ/ת משפטי", "מזכיר/ה בכיר/ה", "מזכיר/ה משפטי/ת", "מזכיר/ה עברית", "מנהל/ת אדמיניסטרטיבי/ת", "מנהל/ת התקשרויות", "מנהל/ת משרד", "מסלק/ת תביעות", "מתמחה - כספים", "סטודנט/ית למשפטים", "עורך/ת דין", "עורך/ת דין נדל\"ן", "פקיד/ה", "פקיד/ת קבלה", "רכז/ת אדמיניסטרטיבי/ת", "Back office - אדמיניסטרציה"};
                        break;

                    case "פרסום / מדיה / תקשורת":
                        specificJobs = new String[]{"איש/ת מכירות", "איש/ת שיווק", "יחסי ציבור", "כותב/ת תוכן", "מנהל/ת מדיה", "מנהל/ת פרסום", "מנהל/ת שיווק דיגיטלי", "מעצב/ת גרפי/ת", "מפיק/ת אירועים", "משרות כלליות", "נציג/ת טלמרקטינג", "נציג/ת מכירות", "עובד/ת דפוס", "עובד/ת ייצור", "עורך/ת / מנהל/ת תוכן", "עורך/ת וידאו / אפטריסט/ית", "פרסום - כללי", "קריאייטיב", "תקציבאי/ת"};
                        break;

                    case "קמעונאות":
                        specificJobs = new String[]{"אחראי/ת משמרת", "איש/ת מכירות", "לוגיסטיקה כללי", "מוכר/ת", "מחסנאי/ת", "מלגזן/ית", "מלקט/ת", "מנהל/ת מכירות", "מנהל/ת סניף", "מנהל/ת צוות", "מנהל/ת קמעונאות", "משרות כלליות", "נציג/ת טלמרקטינג", "נציג/ת מכירות", "נציג/ת מכירות פרונטליות", "סדרן/ית", "סטודנט/ית", "קופאי/ת", "שירות לקוחות"};
                        break;

                    case "רכב / תחבורה":
                        specificJobs = new String[]{"בוחן/ת רכב", "דיאגנוסטיקה", "חשמלא/ית רכב", "יועץ/ת שירות ומכירות רכב", "מכונאי/ת רכב", "מנהל/ת אולם תצוגה", "משרות כלליות", "נהג/ת", "נהג/ת אוטובוס / הסעות", "נהג/ת ג'", "נהג/ת חלוקה", "נהג/ת מעל 15 טון", "נהג/ת שינוע", "סדרן/ית", "קצין/ת בטיחות בתעבורה", "רכב - ניהול", "רכב כללי", "שירות לקוחות", "שליחויות"};
                        break;

                    case "רפואה / בריאות":
                        specificJobs = new String[]{"אח/ות", "גרונטולוג/ית", "הדרכה", "חינוך מיוחד", "טכנאי/ת מכשור רפואי", "יועץ/ת / פסיכולוג/ית", "לבורנט/ית", "מזכיר/ה עברית", "מזכיר/ה רפואי/ת", "משקם/ת / מטפל/ת", "משרות כלליות", "סיעוד", "עובד/ת סוציאלי/ת", "פיזיותרפיסט/ית", "פקיד/ה", "פקיד/ת קבלה", "רפואה - כללי", "רפואה - ניהול", "שירות לקוחות", "Back office - אדמיניסטרציה"};
                        break;

                    case "שיווק":
                        specificJobs = new String[]{"איש/ת מכירות", "איש/ת שיווק", "אנליסט/ית שיווק", "כותב/ת תוכן", "מומחה/ית SEM / PPC", "מנהל/ת מדיה", "מנהל/ת מוצר", "מנהל/ת מותג", "מנהל/ת סושיאל מדיה", "מנהל/ת פרויקטים בשיווק", "מנהל/ת שיווק", "מנהל/ת שיווק דיגיטלי", "מתאם/ת שיווק", "נציג/ת טלמרקטינג", "נציג/ת מכירות", "פרסום - כללי", "Back office - אדמיניסטרציה", "BI", "Data Analyst", "Web Marketing"};
                        break;

                    case "שירות לקוחות":
                        specificJobs = new String[]{"איש/ת מכירות", "טלפן/ית", "מוכר/ת", "מוקדן/ית", "מנהל/ת מוקד", "מנהל/ת צוות", "מנהל/ת שירות", "מנהל/ת תיקי לקוחות", "משרות כלליות", "נציג/ה פרונטלי/ת", "נציג/ת מכירות", "נציג/ת שימור לקוחות", "נציג/ת שירות דיגיטלי", "סטודנט/ית", "פקיד/ה", "שירות לקוחות", "תמיכה טכנית", "Back office - אדמיניסטרציה", "Back office - שירות לקוחות"};
                        break;

                    case "שמירה / אבטחה":
                        specificJobs = new String[]{"אחראי/ת משמרת", "בודק/ת בטחוני/ת", "בקר/ת / מוקדן/ית", "חובש/ת / מע\"ר/ית", "מאבטח/ת", "מוקדן/ית", "מנהל/ת ביטחון", "משרות כלליות", "נהג/ת", "סטודנט/ית", "סייר/ת", "סלקטור/ית", "עבודה מועדפת", "פנסיונר/ית", "פקיד/ת לובי", "קב\"ט/ית", "שומר/ת", "שירות לקוחות", "שמירה ואבטחה – ניהול"};
                        break;

                    case "תיירות / תעופה":
                        specificJobs = new String[]{"אופרציה", "איש/ת מכירות", "דייל/ת", "דייל/ת אוויר / קרקע", "מהנדס/ת אווירונאוטיקה", "מלונאות כללי", "מנהל/ת / אחראי/ת משמרת", "מנהל/ת תיירות / מלונאות", "מסעדנות - ניהול", "משרות כלליות", "נציג/ה פרונטלי/ת", "נציג/ת טלמרקטינג", "סוכן/ת נסיעות", "סטודנט/ית", "עבודה מהבית כללי", "פקח/ית / בקר/ית תעופה", "פקח/ית רחבה", "פקיד/ת קבלה", "שירות לקוחות"};
                        break;

                    case "תעשיה / ייצור":
                        specificJobs = new String[]{"בקרת איכות", "חשמל ואלקטרוניקה", "טכנאי/ת - תעשייה וייצור", "מהנדס/ת תעשייה וניהול", "מכונאי/ת", "מלחים/ה / מחווט/ת", "מנהל/ת ייצור", "מפעיל/ת מכונות", "מרכיב/ה מכאני/ת", "משרות כלליות", "עובד/ת ייצור", "פועל/ת", "קניין/ית", "רכש", "רתכים / מסגרים", "תחזוקה", "תעשיה / ייצור - ניהול", "תפ\"י", "CNC"};

                    default:
                        specificJobs = new String[]{};
                }

                setUpSpinner(spinnerSpecificJob, specificJobs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.jobseeker_menu, menu);
        return true;
    }

    private void setUpSpinner(Spinner spinner, String[] options) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                options
        );

        spinner.setAdapter(adapter);
    }
}
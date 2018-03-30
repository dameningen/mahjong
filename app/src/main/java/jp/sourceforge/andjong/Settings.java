package jp.sourceforge.andjong;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity {
    private static final String SETTINGS_KYOKUSUU = "settings_kyokusuu";

    private static final String SETTINGS_KUITAN = "settings_kuitan";

    private static final boolean SETTINGS_KUITAN_DEF = true;

    private static final String OPT_HINTS = "hints";

    private static final boolean OPT_HINTS_DEF = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO deprecatedメソッド対応
        // addPreferencesFromResource(R.xml.settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingFragment()).commit();

    }

    /**
     * 局数表示ラベル取得。
     * @param context コンテキスト
     * @return 局数取得
     */
    public static String getKyokusuu(Context context) {
        String[] list_entryvalues = context.getResources().getStringArray(
                R.array.list_entryvalues);
        return PreferenceManager.getDefaultSharedPreferences(context).getString(
                SETTINGS_KYOKUSUU, list_entryvalues[0]);
    }

    /**
     * 喰タン有無フラグ取得。
     * @param context コンテキスト
     * @return 喰タン有無フラグ
     */
    public static boolean isKuitan(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SETTINGS_KUITAN, SETTINGS_KUITAN_DEF);
    }

    /**
     * 赤ドラ有無フラグ取得。
     * @param context コンテキスト
     * @return 赤ドラフラグ取得
     */
    public static boolean isAkaDora(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_HINTS, OPT_HINTS_DEF);
    }

    /**
     * 
     */
    public static class SettingFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
    }

}

package jp.sourceforge.andjong.mahjong;

import static jp.sourceforge.andjong.mahjong.Hai.ID_CHUN;
import static jp.sourceforge.andjong.mahjong.Hai.ID_HAKU;
import static jp.sourceforge.andjong.mahjong.Hai.ID_HATSU;
import static jp.sourceforge.andjong.mahjong.Hai.ID_NAN;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PE;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PIN_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PIN_9;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SHA;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SOU_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SOU_9;
import static jp.sourceforge.andjong.mahjong.Hai.ID_TON;
import static jp.sourceforge.andjong.mahjong.Hai.ID_WAN_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_WAN_9;

/**
 * カウントフォーマットを管理するクラスです。
 * @author Yuji Urushibara
 */
public class CountFormat {
    /**
     * カウントを管理するクラスです。
     * @author Yuji Urushibara
     */
    public static class Count {
        /** NK */
        public int m_noKind = 0;

        /** 個数 */
        public int m_num = 0;

        /**
         * カウントを初期化する。
         */
        public void initialize() {
            m_noKind = 0;
            m_num = 0;
        }
    }

    /**
     * 上がりの組み合わせのクラスです。
     * @author Yuji Urushibara
     */
    public static class Combi {
        /** 頭のNK */
        public int m_atamaNoKind = 0;

        /** 順子のNKの配列 */
        public int[] m_shunNoKinds = new int[4];

        /** 順子のNKの配列の有効な個数 */
        public int m_shunNum = 0;

        /** 刻子のNKの配列 */
        public int[] m_kouNoKinds = new int[4];

        /** 刻子のNKの配列の有効な個数 */
        public int m_kouNum = 0;

        /**
         * Combiをコピーする。
         * @param a_dest コピー先のCombi
         * @param a_src コピー元のCombi
         */
        public static void copy(Combi a_dest, Combi a_src) {
            a_dest.m_atamaNoKind = a_src.m_atamaNoKind;

            a_dest.m_shunNum = a_src.m_shunNum;
            System.arraycopy(a_src.m_shunNoKinds, 0, a_dest.m_shunNoKinds, 0,
                    a_dest.m_shunNum);

            a_dest.m_kouNum = a_src.m_kouNum;
            System.arraycopy(a_src.m_kouNoKinds, 0, a_dest.m_kouNoKinds, 0,
                    a_dest.m_kouNum);
        }
    }

    /**
     * 上がりの組み合わせの配列を管理するクラスです。
     * @author Yuji Urushibara
     */
    private static class CombiManage {
        /** 上がりの組み合わせの配列の最大値 */
        public final static int COMBI_MAX = 10;

        /** 上がりの組み合わせの配列 */
        public Combi[] m_combis = new Combi[COMBI_MAX];

        /** 上がりの組み合わせの配列の有効な個数 */
        public int m_combiNum = 0;

        /** カウントの配列の残りの個数 */
        public int m_remain = 0;

        /** 作業領域 */
        public Combi m_work = new Combi();

        {
            for (int i = 0; i < m_combis.length; i++) {
                m_combis[i] = new Combi();
            }
        }

        /**
         * 作業領域を初期化する。
         * @param a_remain カウントの配列の残りの個数
         */
        public void initialize(int a_remain) {
            m_combiNum = 0;
            this.m_remain = a_remain;
            m_work.m_atamaNoKind = 0;
            m_work.m_shunNum = 0;
            m_work.m_kouNum = 0;
        }

        /**
         * 上がりの組み合わせを追加する。
         */
        public void add() {
            Combi.copy(m_combis[m_combiNum++], m_work);
        }
    }

    /** カウントの最大値 */
    public static final int COUNT_MAX = 14 + 2;

    /** カウントの配列 */
    public Count[] m_counts;

    /** カウントの配列の有効な個数 */
    public int m_countNum;

    /** 上がりの組み合わせの配列を管理 */
    private CombiManage m_combiManage = new CombiManage();

    {
        m_counts = new Count[COUNT_MAX];
        for (int i = 0; i < COUNT_MAX; i++) {
            m_counts[i] = new Count();
        }
    }

    /**
     * カウントの配列の長さの合計を取得する。
     * @return カウントの配列の長さの合計
     */
    private int getTotalCountLength() {
        int totalCountLength = 0;

        for (int i = 0; i < m_countNum; i++) {
            totalCountLength += m_counts[i].m_num;
        }

        return totalCountLength;
    }

    /**
     * カウントフォーマットを設定する。
     * @param a_tehai 手牌
     * @param a_addHai 追加する牌
     */
    public void setCountFormat(Tehai a_tehai, Hai a_addHai) {
        for (Count count : m_counts) {
            count.initialize();
        }
        m_countNum = 0;

        int addHaiNoKind = 0;
        boolean set = true;
        if (a_addHai != null) {
            addHaiNoKind = a_addHai.getNoKind();
            set = false;
        }

        int jyunTehaiNoKind;
        int jyunTehaiLength = a_tehai.getJyunTehaiLength();
        for (int i = 0; i < jyunTehaiLength;) {
            jyunTehaiNoKind = (a_tehai.getJyunTehai())[i].getNoKind();

            if (!set && (jyunTehaiNoKind > addHaiNoKind)) {
                set = true;
                m_counts[m_countNum].m_noKind = addHaiNoKind;
                m_counts[m_countNum].m_num = 1;
                m_countNum++;
                continue;
            }

            m_counts[m_countNum].m_noKind = jyunTehaiNoKind;
            m_counts[m_countNum].m_num = 1;

            if (!set && (jyunTehaiNoKind == addHaiNoKind)) {
                set = true;
                m_counts[m_countNum].m_num++;
            }

            while (++i < jyunTehaiLength) {
                if (jyunTehaiNoKind == (a_tehai.getJyunTehai())[i]
                        .getNoKind()) {
                    m_counts[m_countNum].m_num++;
                } else {
                    break;
                }
            }

            m_countNum++;
        }

        if (!set) {
            m_counts[m_countNum].m_noKind = addHaiNoKind;
            m_counts[m_countNum].m_num = 1;
            m_countNum++;
        }

        for (int i = 0; i < m_countNum; i++) {
            if (m_counts[i].m_num > 4) {
                // 5つ目の追加牌は無効とする。
                m_counts[i].m_num--;
            }
        }
    }

    /**
     * 上がりの組み合わせの配列を取得する。
     * @param a_combis 上がりの組み合わせの配列
     * @return 上がりの組み合わせ
     */
    public int getCombis(Combi[] a_combis) {
        // public int getCombis(Combi[] a_combis) {
        m_combiManage.initialize(getTotalCountLength());
        searchCombi(0);
        // a_combis = m_combiManage.m_combis;
        if (m_combiManage.m_combiNum == 0) {
            m_chiitoitsu = checkChiitoitsu();
            if (m_chiitoitsu) {
                m_combiManage.m_combiNum = 1;
            } else {
                m_kokushi = checkKokushi();
                if (m_kokushi) {
                    m_combiManage.m_combiNum = 1;
                }
            }
        }
        return m_combiManage.m_combiNum;
    }

    public Combi[] getCombis() {
        return m_combiManage.m_combis;
    }

    public int getCombiNum() {
        return m_combiManage.m_combiNum;
    }

    private boolean m_chiitoitsu;

    public boolean isChiitoitsu() {
        return m_chiitoitsu;
    }

    /**
     * 七対子チェック処理。
     * @return 対子が7つ有る場合はtrue、そうでない場合はfalse
     */
    private boolean checkChiitoitsu() {
        int count = 0;
        for (int i = 0; i < m_countNum; i++) {
            if (m_counts[i].m_num == 2) {
                count++;
            } else {
                return false;
            }
        }

        return (count == 7);
    }

    private boolean m_kokushi;

    public boolean isKokushi() {
        return m_kokushi;
    }

    /**
     * 国士無双チェック処理。
     * @return 国士無双の条件を満たしている場合はtrue、そうでない場合はfalse
     */
    private boolean checkKokushi() {
        // 牌の数を調べるための配列 (0番地は使用しない）
        int checkId[] = { ID_WAN_1, ID_WAN_9, ID_PIN_1, ID_PIN_9, ID_SOU_1,
                ID_SOU_9, ID_TON, ID_NAN, ID_SHA, ID_PE, ID_HAKU, ID_HATSU,
                ID_CHUN };
        int countHaiArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        // 手牌のIDを検索する
        for (int i = 0; i < m_countNum; i++) {
            for (int j = 0; j < checkId.length; j++) {
                if (Hai.noKindToId(m_counts[i].m_noKind) == checkId[j]) {
                    countHaiArray[j] = m_counts[i].m_num;
                }
            }
        }

        boolean hasAllYaoChuHai = true;
        boolean hasAtama = false;
        // 国士無双が成立しているか調べる(手牌がすべて1.9字牌 すべての１,９字牌を持っている）
        for (int countHai : countHaiArray) {
            // 0枚の牌があれば不成立
            if (countHai == 0) {
                hasAllYaoChuHai = false;
                break;
            }
            if (countHai == 2) {
                hasAtama = true;
            }
        }
        // 条件を満たしていれば成立
        return (hasAllYaoChuHai && hasAtama);
    }

    /**
     * 上がりの組み合わせを再帰的に探す。
     * @param a_iSearch 検索位置
     */
    private void searchCombi(int a_iSearch) {
        // 検索位置を更新する。
        for (; a_iSearch < m_countNum; a_iSearch++) {
            if (m_counts[a_iSearch].m_num > 0) {
                break;
            }
        }

        if (a_iSearch >= m_countNum) {
            return;
        }

        // 頭をチェックする。
        if (m_combiManage.m_work.m_atamaNoKind == 0) {
            if (m_counts[a_iSearch].m_num >= 2) {
                // 頭を確定する。
                m_counts[a_iSearch].m_num -= 2;
                m_combiManage.m_remain -= 2;
                m_combiManage.m_work.m_atamaNoKind = m_counts[a_iSearch].m_noKind;

                // 上がりの組み合わせを見つけたら追加する。
                if (m_combiManage.m_remain <= 0) {
                    m_combiManage.add();
                } else {
                    searchCombi(a_iSearch);
                }

                // 確定した頭を戻す。
                m_counts[a_iSearch].m_num += 2;
                m_combiManage.m_remain += 2;
                m_combiManage.m_work.m_atamaNoKind = 0;
            }
        }

        // 順子をチェックする。
        int left = a_iSearch;
        int center = a_iSearch + 1;
        int right = a_iSearch + 2;
        if (!Hai.isTsuu(m_counts[left].m_noKind)) {
            if ((m_counts[left].m_noKind + 1 == m_counts[center].m_noKind)
                    && (m_counts[center].m_num > 0)) {
                if ((m_counts[left].m_noKind + 2 == m_counts[right].m_noKind)
                        && (m_counts[right].m_num > 0)) {
                    // 順子を確定する。
                    m_counts[left].m_num--;
                    m_counts[center].m_num--;
                    m_counts[right].m_num--;
                    m_combiManage.m_remain -= 3;
                    m_combiManage.m_work.m_shunNoKinds[m_combiManage.m_work.m_shunNum] = m_counts[left].m_noKind;
                    m_combiManage.m_work.m_shunNum++;

                    // 上がりの組み合わせを見つけたら追加する。
                    if (m_combiManage.m_remain <= 0) {
                        m_combiManage.add();
                    } else {
                        searchCombi(a_iSearch);
                    }

                    // 確定した順子を戻す。
                    m_counts[left].m_num++;
                    m_counts[center].m_num++;
                    m_counts[right].m_num++;
                    m_combiManage.m_remain += 3;
                    m_combiManage.m_work.m_shunNum--;
                }
            }
        }

        // 刻子をチェックする。
        if (m_counts[a_iSearch].m_num >= 3) {
            // 刻子を確定する。
            m_counts[a_iSearch].m_num -= 3;
            m_combiManage.m_remain -= 3;
            m_combiManage.m_work.m_kouNoKinds[m_combiManage.m_work.m_kouNum] = m_counts[a_iSearch].m_noKind;
            m_combiManage.m_work.m_kouNum++;

            // 上がりの組み合わせを見つけたら追加する。
            if (m_combiManage.m_remain <= 0) {
                m_combiManage.add();
            } else {
                searchCombi(a_iSearch);
            }

            // 確定した刻子を戻す。/
            m_combiManage.m_remain += 3;
            m_counts[a_iSearch].m_num += 3;
            m_combiManage.m_work.m_kouNum--;
        }
    }
}

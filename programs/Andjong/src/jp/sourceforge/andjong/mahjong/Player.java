package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

/**
 * プレイヤーを管理するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
class Player {
	/** EventIF */
	private EventIf eventIf;

	/**
	 * EventIFを取得します。。
	 *
	 * @return EventIF
	 */
	EventIf getEventIf() {
		return eventIf;
	}

	/** 手牌 */
	private Tehai m_tehai = new Tehai();

	/**
	 * 手牌を取得します。
	 *
	 * @return 手牌
	 */
	Tehai getTehai() {
		return m_tehai;
	}

	/** 河 */
	private Kawa kawa = new Kawa();

	/**
	 * 河を取得します。
	 *
	 * @return 河
	 */
	Kawa getKawa() {
		return kawa;
	}

	/** 自風 */
	private int jikaze;

	/**
	 * 自風を取得します。
	 *
	 * @return 自風
	 */
	int getJikaze() {
		return jikaze;
	}

	/**
	 * 自風を設定します。
	 *
	 * @param jikaze
	 *            自風
	 */
	void setJikaze(int jikaze) {
		this.jikaze = jikaze;
	}

	/** 点棒 */
	private int tenbou;

	/**
	 * 点棒を取得します。
	 *
	 * @return
	 */
	int getTenbou() {
		return tenbou;
	}

	/**
	 * 点棒を設定します。
	 *
	 * @param tenbou
	 *            点棒
	 */
	void setTenbou(int tenbou) {
		this.tenbou = tenbou;
	}

	/**
	 * 点棒を増やします。
	 *
	 * @param ten
	 *            点
	 */
	void increaseTenbou(int ten) {
		tenbou += ten;
	}

	/**
	 * 点棒を減らします。
	 *
	 * @param ten
	 *            点
	 */
	void reduceTenbou(int ten) {
		tenbou -= ten;
	}

	/** リーチ */
	private boolean reach;

	/**
	 * リーチを取得します。
	 *
	 * @return リーチ
	 */
	boolean isReach() {
		return reach;
	}

	/**
	 * リーチを設定します。
	 *
	 * @param reach
	 *            リーチ
	 */
	void setReach(boolean reach) {
		this.reach = reach;
	}


	private CountFormat m_countFormat = new CountFormat();

	boolean isTenpai() {
		if (reach) {
			return true;
		}

		Hai addHai;
		for (int id = 0; id < Hai.ID_ITEM_MAX; id++) {
			addHai = new Hai(id);
			m_countFormat.setCountFormat(m_tehai, addHai);
			if (m_countFormat.getCombis(null) > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * プレイヤーを初期化する。
	 *
	 * @param eventIf
	 *            EventIF
	 */
	Player(EventIf eventIf) {
		this.eventIf = eventIf;
	}

	/**
	 * プレイヤーを初期化します。
	 */
	void init() {
		// 手牌を初期化します。
		m_tehai.initialize();

		// 河を初期化します。
		kawa.initialize();

		// リーチを初期化します。
		reach = false;
	}
}

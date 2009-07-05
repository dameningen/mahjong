package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Info.*;

/**
 * UIを実装するクラスです。 とりあえず、コンソールで実装します。 TODO オーバーライドしやすい設計にする。
 * 
 * @author Yuji Urushibara
 * 
 */
public class UI {
	private Info info;
	private Tehai tehai = new Tehai();
	private Hai[] jyunTehai = new Hai[Tehai.JYUNTEHAI_MAX];
	private Kawa kawa = new Kawa();
	private KawaHai[] kawaHais = new KawaHai[Kawa.KAWA_MAX];

	{
		for (int i = 0; i < Tehai.JYUNTEHAI_MAX; i++)
			jyunTehai[i] = new Hai();

		for (int i = 0; i < Kawa.KAWA_MAX; i++)
			kawaHais[i] = new KawaHai();
	}

	public UI(Info info) {
		this.info = info;
	}

	static public String idToString(int id) {
		int kind = id & (Hai.KIND_SHUU | Hai.KIND_TSUU);
		id &= ~(Hai.KIND_SHUU | Hai.KIND_TSUU);

		switch (kind) {
		case Hai.KIND_WAN:
			switch (id) {
			case 1:
				return "一";
			case 2:
				return "二";
			case 3:
				return "三";
			case 4:
				return "四";
			case 5:
				return "五";
			case 6:
				return "六";
			case 7:
				return "七";
			case 8:
				return "八";
			case 9:
				return "九";
			}
			break;
		case Hai.KIND_PIN:
			switch (id) {
			case 1:
				return "�@";
			case 2:
				return "�A";
			case 3:
				return "�B";
			case 4:
				return "�C";
			case 5:
				return "�D";
			case 6:
				return "�E";
			case 7:
				return "�F";
			case 8:
				return "�G";
			case 9:
				return "�H";
			}
			break;
		case Hai.KIND_SOU:
			switch (id) {
			case 1:
				return "１";
			case 2:
				return "２";
			case 3:
				return "３";
			case 4:
				return "４";
			case 5:
				return "５";
			case 6:
				return "６";
			case 7:
				return "７";
			case 8:
				return "８";
			case 9:
				return "９";
			}
			break;
		case Hai.KIND_FON:
			switch (id) {
			case 1:
				return "東";
			case 2:
				return "南";
			case 3:
				return "西";
			case 4:
				return "北";
			}
			break;
		case Hai.KIND_SANGEN:
			switch (id) {
			case 1:
				return "白";
			case 2:
				return "發";
			case 3:
				return "中";
			}
			break;
		}

		return null;
	}

	static public String jikazeToString(int jikaze) {
		switch (jikaze) {
		case 0:
			return "東";
		case 1:
			return "南";
		case 2:
			return "西";
		case 3:
			return "北";
		}

		return null;
	}

	public void event(int eventCallPlayerIdx, int eventTargetPlayerIdx,
			int eventId) {
		int jyunTehaiLength;
		switch (eventId) {
		case EVENTID_TSUMO:
			System.out.print("[" + jikazeToString(info.getJikaze()) + "]");
			System.out.print("[自摸]");

			// 純手牌を表示します。
			info.copyTehai(tehai, 0);
			jyunTehaiLength = tehai.copyJyunTehai(jyunTehai);
			for (int i = 0; i < jyunTehaiLength; i++)
				System.out.print(idToString(jyunTehai[i].getId()));
			System.out.println(":" + idToString((info.getTsumoHai()).getId()));
			break;
		case EVENTID_SUTEHAI:
			if (eventCallPlayerIdx == 0) {
				System.out.print("[" + jikazeToString(info.getJikaze()) + "]");
				System.out.print("[捨牌]");
				info.copyKawa(kawa, 0);
				int kawaLength = kawa.copyKawaHai(kawaHais);
				for (int i = 0; i < kawaLength; i++)
					System.out.print(idToString(kawaHais[i].getId()));
				System.out.println(":"
						+ idToString((info.getSuteHai()).getId()));
			}
			break;
		case EVENTID_RON:
			System.out.print("[" + jikazeToString(info.getJikaze()) + "]");
			System.out.print("[栄和]");

			// 純手牌を表示します。
			info.copyTehai(tehai, 0);
			jyunTehaiLength = tehai.copyJyunTehai(jyunTehai);
			for (int i = 0; i < jyunTehaiLength; i++)
				System.out.print(idToString(jyunTehai[i].getId()));
			System.out.println(":" + idToString((info.getSuteHai()).getId()));
			break;
		case EVENTID_TSUMOAGARI:
			System.out.print("[" + jikazeToString(info.getJikaze()) + "]");
			System.out.print("[自摸和]");

			// 純手牌を表示します。
			info.copyTehai(tehai, 0);
			jyunTehaiLength = tehai.copyJyunTehai(jyunTehai);
			for (int i = 0; i < jyunTehaiLength; i++)
				System.out.print(idToString(jyunTehai[i].getId()));
			System.out.println(":" + idToString((info.getTsumoHai()).getId()));
			break;
		case EVENTID_NAGASHI:
			// System.out.println("[" + eventCallPlayerIdx + "]["
			// + eventTargetPlayerIdx + "]EVENTID_NAGASHI");
			break;
		default:
			break;
		}
	}
}

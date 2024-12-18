package com.example.tt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TongueTwisterDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tongue_twisters.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE tongue_twisters (" +
                    "_id INTEGER PRIMARY KEY," +
                    "phrase TEXT," +
                    "language TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS tongue_twisters";

    public TongueTwisterDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public TongueTwisterDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // 한국어 문구 추가
        String[] koreanPhrases = {
                "간 구하려 광고해 간구한 강가의 관광객들",
                "간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 장 공장장이다",
                "경찰청 철창살은 외철창살이고 검찰청 철창살은 쌍철창살이다",
                "고려고교 교복은 고급교복이고 고려고교 교복은 고급원단을 사용했다",
                "굴뚝벽돌 벽돌굴뚝",
                "궁중 숭늉 중 눌은 궁중 숭늉은 늘 눅눅",
                "그 사건의 타당성 확률로 이 사건이 종말이 될 수 있지 아니할 수 있다고 고려할 수 있을 수 있지 않을 수도 있을지 모를 수도 없을까라는 생각이 들 수가 있다고 언급할 수 없을 수도 있을 수 있지 아니할 수가 없다",
                "내가 그린 기린 그림은 목이 긴 기린 그림이고, 네가 그린 기린 그림은 목이 안 긴 기린 그림이다",
                "널 뜬 난 달 뜬 날 딴 나라로 떠날 따름이야",
                "도토리가 문을 도로록, 드르륵, 두루룩 열었는가? 드로록, 도루륵, 두르룩 열었는가",
                "들의 콩깍지는 깐 콩깍지냐, 안 깐 콩깍지냐. 깐 콩깍지면 어떻고 안 깐 콩깍지면 어떠냐. 깐 콩깍지나 안 깐 콩깍지나 콩깍지는 다 콩깍지인데",
                "땅콩 깡통 중 저 땅콩 깡통은 깐 땅콩 깡통이냐, 안 깐 땅콩 깡통이냐",
                "단단한 단 당근과 단단한 안 단 당근, 안 단단한 단 당근과 안 단단한 안 단 당근",
                "목동 로얄 뉴로얄 레스토랑 뉴메뉴 미트소시지소스스테이크 크림소시지소스스파게티",
                "민망한 열망만 말하는 열 명만 살던 멸망한 마을엔 말만 많은 만만한 말만 만 마리 남았다 말하네",
                "박범복군은 밤벚꽃놀이를 가고 방범복양은 낮벚꽃놀이를 간다",
                "백 법학박사님 댁 밥은 백발백중 질고 박 법학박사님 댁 밥은 백발백중 되다",
                "빼빼로 나라에 사는 빼빼 마른 빼빼로가 아몬드 빼빼로 나라에 사는 친구 안 빼빼 마른 빼빼로를 보고 \"살 빼\" 하니까 안 빼빼 마른 빼빼로가 빼액빼액 화를 내며 빼빼로 나라로 돌아갔대요",
                "뽕나무 숲의 뽕나무는 뽕뽕 방귀 낀 뽕나무인가 뽕뽕뽕 방귀 뀐 뽕나무인가",
                "삭에 사과하는 사근사근하고 싹싹한 삵과 삭은 사과싹 싹 사가",
                "산골 찹쌀 촌 찹쌀 갯골 찹쌀 햇찹쌀",
                "상표 붙인 큰 깡통은 깐 깡통인가? 안 깐 깡통인가",
                "서울특별시 특허허가과 허가과장 허과장",
                "섬과 성에 섬광탄이 있었다. 섬의 이름은 섬성 삼상 섬이고 성의 이름은 상삼 성섬이다 성수동 성수로 섬광탄을 터뜨려 섬과 성에 큰일이 났다는 것이다",
                "신진 샹송가수의 신춘 샹송쇼",
                "신춘 샹송쇼를 샹그릴라 호텔에서 연 신진 샹송가수 송상성씨가 저기 저 미트 소시지 소스 스파게티는 깐쇼새우 크림 소스 소시지 소스 스테이크보다 비싸다며 단식에 들어가 호텔의 빈축을 사고 있습니다",
                "안 촉촉한 초코칩 나라에 살던 안 촉촉한 초코칩이 촉촉한 초코칩 나라의 촉촉한 초코칩을 보고 촉촉한 초코칩이 되고 싶어서 촉촉한 초코칩 나라에 갔는데, 촉촉한 초코칩 나라의 촉촉한 문지기가 \"넌 촉촉한 초코칩이 아니고 안 촉촉한 초코칩이니까 안 촉촉한 초코칩 나라에서 살아\"라고 해서 안 촉촉한 초코칩은 촉촉한 초코칩이 되는 것을 포기하고 안 촉촉한 눈물을 흘리며 안 촉촉한 초코칩 나라로 돌아갔다",
                "알파카 털로 만든 알파카파카 입은 알파카파카알파카가 알파카파카를 팔면 알파카파카 파는 알파카파카알파카",
                "앞집 팥죽은 붉은 팥 풋팥죽이고, 뒷집 콩죽은 햇콩단콩 콩죽, 우리집 깨죽은 검은깨 깨죽인데 사람들은 햇콩 단콩 콩죽 깨죽 죽먹기를 싫어하더라",
                "육통통장 적금통장은 황색적금통장이고, 팔통통장 적금통장은 녹색적금통장이다",
                "우리집 옆집 앞집 뒤창살은 홑겹창살이고, 우리집 뒷집 앞집 옆창살은 겹홑창살이다",
                "이런 기린 저런 기린 긴 기린 안 긴 기린 그린 기린",
                "이병원 병원의 원장 이병원 원장의 이병원 원장 원장실에 들어가기 위한 원장실 키",
                "일도일동 일도이동 이도일동 이도이동",
                "저 건너 신진사 집 시렁 위에 얹은 청동 청정미가 청차좁쌀이냐 쓿은 청동 청정미 청차좁쌀이냐 아니 쓿은 청동 청정미 청차좁쌀이냐",
                "저기 계신 저 분이 박 법학박사이시고, 여기 계신 이분이 백 법학박사이시다",
                "저기 있는 말뚝이 말 맬 말뚝이냐, 말 못 맬 말뚝이냐",
                "저기 있는 한국 화물 항공기가 출발할 한국 화물 항공기인가, 출발 안 할 한국 화물 항공기인가",
                "저기 저 뜀틀이 내가 뛸 뜀틀인가 내가 안 뛸 뜀틀인가",
                "정경 담당 정 선생님 상담 담당 성 선생님 강당 담당 강 선생님 중앙 담당 동 선생님",
                "챠프포프킨과 치스챠코프는 라흐마니노프의 피아노 콘체르토의 선율이 흐르는 청단풍잎 홍단풍잎 흑단풍잎 백단풍잎",
                "지각을 지각했을때 지갑으로 지각해서 지갑을 지각하라는 지시을 받았다",
                "철수 책상 철책상",
                "초밥먹고 쌀밥먹고 쌈밥먹고 짬밥먹고 치밥먹고 혼밥먹고 쉰밥먹고 콩밥먹고 찬밥먹고 흰밥먹고",
                "큰 토끼통 옆 작은 토끼통, 작은 토끼통 옆 큰 토끼통",
                "특히 턱 긴 특급 터키 토끼 특기: 토기 톺기, 튀김 뜯기, 땅콩 투기",
                "한국관광공사 곽진광 관광과장"

        };
        for (String phrase : koreanPhrases) {
            insertPhrase(db, phrase, "korean");
        }

        // 영어 문구 추가
        String[] englishPhrases = {
                "Betty Botter bought a bit of butter. The butter Betty Botter bought was a bit bitter, and made her batter bitter. But a bit of better butter makes batter better. So Betty Botter bought a bit of better butter, making Betty Botter's bitter batter better.",
                "Black Jack Black eating Cracker Jack playing a black jack while playing Black Jack with Jack Black who is threatening Black Jack Black with a Black Jack",
                "Can you can a can as a canner can can a can?",
                "Can you imagine an imaginary menagerie manager imagining managing an imaginary menagerie?",
                "Chester chooses chestnuts, cheddar cheese with chewy chives.",
                "Fuzzy Wuzzy was a bear. Fuzzy Wuzzy had no hair. Fuzzy Wuzzy wasn’t fuzzy, was he?",
                "Green Glass Grows Globes Greenly.",
                "Globglogabgalab",
                "How much wood would a woodchuck chuck if a woodchuck could chuck wood?",
                "He would chuck, he would, as much as he could, and chuck as much wood",
                "As a woodchuck would if a woodchuck could chuck wood",
                "I saw a kitten eating chicken in the kitchen.",
                "I thought a thought, but the thought that I thought was not the thought I thought I thought.",
                "If two witches were watching each watches, which witch would watch which watch?",
                "Luke Luck likes lakes. Luke's duck likes lakes. Luke Luck licks lakes. Luck's duck licks lakes. Duck takes licks in lakes Luke Luck likes. Luke Luck takes licks in lakes duck likes.",
                "Marry Mac's mother's(is) making Marry Mac marry me, my mother's(is) making me marry Marry Mac.",
                "Moses, he knowses his toeses aren't roses as Moses supposes his toeses to be.",
                "Moses supposes his toeses are roses, but Moses supposes erroneously.",
                "One-one was a racehorse. Two-two was one, too. When One-one won one race, Two-two won one, too.",
                "Peter Piper picked a peck of pickled peppers. A peck of pickled peppers Peter Piper picked. If Peter Piper picked a peck of pickled peppers, Where's the peck of pickled peppers that Peter Piper picked?",
                "She sells sea shells on the seashore. The shells she sells are surely seashells. So if she sells shells on the seashore, I'm sure she sells seashore shells",
                "Ship shipping ships shipping shipping ships",
                "Sinful Caesar sipped his snifter seized his knees and sneezed",
                "Sven said, \"Ted, send ten tents.\". Ted said, \"Sven, send ten cents.\". When Sven sent Ted ten cents, When Ted sent Sven ten tents.",
                "The sixth sick Sheik's sixth sheep's sick",
                "Texas taxes tax us",
                "Those chestnuts, cheddar cheese and chives in cheery, charming chunks.",
                "Tom threw Tim's three thumbtacks",
                "What mine in the mine to mine with mine is mine? Mine might be a mine nine times mightier than the mine with nine miners. Nine miners might find mine while mighty finely tryin",
                "Will Will Smith smith Will Smith? Yes, Will Smith will smith Will Smith"
        };

        for (String phrase : englishPhrases) {
            insertPhrase(db, phrase, "english");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private void insertPhrase(SQLiteDatabase db, String phrase, String language) {
        String sql = "INSERT INTO tongue_twisters (phrase, language) VALUES (?, ?)";
        db.execSQL(sql, new Object[]{phrase, language});
    }
}
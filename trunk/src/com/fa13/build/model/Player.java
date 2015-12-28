package com.fa13.build.model;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Player extends BasicPlayer implements Comparable<Player>, Cloneable {
	
	public Player(){
		super();
	}
	
	protected Player(PlayerAmplua position){
		super(position);
	}
	
	public Player(int number, String name, String nationalityCode,
			PlayerAmplua position, int age, int talent, int experience,
			int fitness, int morale, int strength, int health, int price,
			int salary, int shooting, int passing, int cross, int dribbling,
			int tackling, int speed, int heading, int stamina, int reflexes,
			int handling, int disqualification, int rest, int teamwork,
			int games, int goalsTotal, int goalsMissed, int goalsChamp,
			double mark, int gamesCareer, int goalsCareer, int yellowCards,
			int redCards, boolean transfer, boolean lease, String birthplace,
			Date birthdate, int birthtour, int assists, int profit, int id) {
		super(position);
		this.number = number;
		this.name = name;
		this.nationalityCode = nationalityCode;
		this.position = position;
		this.age = age;
		this.talent = talent;
		this.experience = experience;
		this.fitness = fitness;
		this.morale = morale;
		this.strength = strength;
		this.health = health;
		this.price = price;
		this.salary = salary;
		this.shooting = shooting;
		this.passing = passing;
		this.cross = cross;
		this.dribbling = dribbling;
		this.tackling = tackling;
		this.speed = speed;
		this.heading = heading;
		this.stamina = stamina;
		this.reflexes = reflexes;
		this.handling = handling;
		this.disqualification = disqualification;
		this.rest = rest;
		this.teamwork = teamwork;
		this.games = games;
		this.goalsTotal = goalsTotal;
		this.goalsMissed = goalsMissed;
		this.goalsChamp = goalsChamp;
		this.mark = mark;
		this.gamesCareer = gamesCareer;
		this.goalsCareer = goalsCareer;
		this.yellowCards = yellowCards;
		this.redCards = redCards;
		this.transfer = transfer;
		this.lease = lease;
		this.birthplace = birthplace;
		this.birthdate = birthdate;
		this.birthtour = birthtour;
		this.assists = assists;
		this.profit = profit;
		this.id = id;
	}
	
	protected String nationalityCode;
	protected int age;
	protected int talent;
	protected int experience;
	protected int fitness;
	protected int morale;
	protected int strength;
	protected int health;
	protected int price;
	protected int salary;
	protected int shooting;
	protected int passing;
	protected int cross;
	protected int dribbling;
	protected int tackling;
	protected int speed;
	protected int heading;
	protected int stamina;
	protected int reflexes;
	protected int handling;
	protected int disqualification;
	protected int rest;
	protected int teamwork;
	protected int games;
	protected int goalsTotal;
	protected int goalsMissed;
	protected int goalsChamp;
	protected double mark;
	protected int gamesCareer;
	protected int goalsCareer;
	protected int yellowCards;
	protected int redCards;
	protected boolean transfer;
	protected boolean lease;
	protected String birthplace;
	protected Date birthdate;
	protected int birthtour;
	protected int assists;
	protected int profit;
	
	public int compareTo(Player o) {
		if (this.id == o.id) return 0;
		if (this.position.compareTo(o.position) == 0) {
			return this.number - o.number;
		}
		return this.position.compareTo(o.position);
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public PlayerAmplua getPosition() {
		return position;
	}
	
	public PlayerAmplua getOriginalPosition() {
		return originalPosition;
	}

	public void setPosition(PlayerAmplua position) {
		this.position = position;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getTalent() {
		return talent;
	}

	public void setTalent(int talent) {
		this.talent = talent;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getFitness() {
		return fitness;
	}
	
	public int getFitness(int restDays) {
		int fit = this.fitness;
		if (fit >= 70) {
			if (this.rest < 12) {
				restDays = Math.min(restDays, Math.max(12 - this.rest, 0));
				fit += Math.min(restDays, Math.max(2 - this.rest, 0));
				fit += restDays;
			}
		} else {
			int toNormal = 70 - fit;
			fit += Math.min(toNormal, restDays);
			if (restDays > toNormal) {
				restDays -= toNormal;
				if (this.rest + toNormal < 12) {
					restDays = Math.min(restDays, Math.max(12 - (this.rest + toNormal), 0));
					fit += Math.min(restDays, Math.max(2 - (this.rest + toNormal), 0));
					fit += restDays;
				}
			}
		}
		return Math.min(this.health, fit);
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public int getMorale() {
		return morale;
	}

	public void setMorale(int morale) {
		this.morale = morale;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getShooting() {
		return shooting;
	}

	public void setShooting(int shooting) {
		this.shooting = shooting;
	}

	public int getPassing() {
		return passing;
	}

	public void setPassing(int passing) {
		this.passing = passing;
	}

	public int getCross() {
		return cross;
	}

	public void setCross(int cross) {
		this.cross = cross;
	}

	public int getDribbling() {
		return dribbling;
	}

	public void setDribbling(int dribbling) {
		this.dribbling = dribbling;
	}

	public int getTackling() {
		return tackling;
	}

	public void setTackling(int tackling) {
		this.tackling = tackling;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getReflexes() {
		return reflexes;
	}

	public void setReflexes(int reflexes) {
		this.reflexes = reflexes;
	}

	public int getHandling() {
		return handling;
	}

	public void setHandling(int handling) {
		this.handling = handling;
	}

	public int getDisqualification() {
		return disqualification;
	}

	public void setDisqualification(int disqualification) {
		this.disqualification = disqualification;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}

	public int getTeamwork() {
		return teamwork;
	}

	public void setTeamwork(int teamwork) {
		this.teamwork = teamwork;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public int getGoalsTotal() {
		return goalsTotal;
	}

	public void setGoalsTotal(int goalsTotal) {
		this.goalsTotal = goalsTotal;
	}

	public int getGoalsMissed() {
		return goalsMissed;
	}

	public void setGoalsMissed(int goalsMissed) {
		this.goalsMissed = goalsMissed;
	}

	public int getGoalsChamp() {
		return goalsChamp;
	}

	public void setGoalsChamp(int goalsChamp) {
		this.goalsChamp = goalsChamp;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public int getGamesCareer() {
		return gamesCareer;
	}

	public void setGamesCareer(int gamesCareer) {
		this.gamesCareer = gamesCareer;
	}

	public int getGoalsCareer() {
		return goalsCareer;
	}

	public void setGoalsCareer(int goalsCareer) {
		this.goalsCareer = goalsCareer;
	}

	public int getYellowCards() {
		return yellowCards;
	}

	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}

	public int getRedCards() {
		return redCards;
	}

	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public boolean isLease() {
		return lease;
	}

	public void setLease(boolean lease) {
		this.lease = lease;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getBirthtour() {
		return birthtour;
	}

	public void setBirthtour(int birthtour) {
		this.birthtour = birthtour;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getRealStrength (boolean homeBonus, int restDays) {
		return this.strength * (this.morale * 0.01) * (this.getFitness(restDays) * 0.01) 
			* (1 + this.teamwork * 0.00125) * (homeBonus ? 1.10 : 1) 
			* (PlayerAmplua.positionCoefficients[this.position.ordinal()][this.originalPosition.ordinal()]);
	}
	
	public static final Map<String, String> nationalities;
	
	static {
		Map<String, String> tmp = new TreeMap<String, String>();
		tmp.put("Афганистан", "afg");
		tmp.put("Ангилья", "aia");
		tmp.put("Албания", "alb");
		tmp.put("Алжир", "alg");
		tmp.put("Андорра", "and");
		tmp.put("Ангола", "ang");
		tmp.put("Нидерландские Антильские острова", "ant");
		tmp.put("Аргентина", "arg");
		tmp.put("Армения", "arm");
		tmp.put("Аруба", "aru");
		tmp.put("Американское Самоа", "asa");
		tmp.put("Антигуа", "atg");
		tmp.put("Австралия", "aus");
		tmp.put("Австрия", "aut");
		tmp.put("Азербайджан", "aze");
		tmp.put("Багамы", "bah");
		tmp.put("Бангладеш", "ban");
		tmp.put("Бурунди", "bdi");
		tmp.put("Бельгия", "bel");
		tmp.put("Бенин", "ben");
		tmp.put("Бермуды", "ber");
		tmp.put("Буркина-Фасо", "bfa");
		tmp.put("Бахрейн", "bhr");
		tmp.put("Бутан", "bhu");
		tmp.put("Беларусь", "blr");
		tmp.put("Белиз", "blz");
		tmp.put("Боливия", "bol");
		tmp.put("Ботсвана", "bot");
		tmp.put("Бразилия", "bra");
		tmp.put("Барбадос", "brb");
		tmp.put("Бруней", "bru");
		tmp.put("Болгария", "bul");
		tmp.put("Камбоджа", "cam");
		tmp.put("Канада", "can");
		tmp.put("Каймановы острова", "cay");
		tmp.put("Конго", "cgo");
		tmp.put("Чад", "cha");
		tmp.put("Чили", "chi");
		tmp.put("Китай", "chn");
		tmp.put("Кот Д`Ивуар", "civ");
		tmp.put("Камерун", "cmr");
		tmp.put("ДР Конго", "cod");
		tmp.put("Острова Кука", "cok");
		tmp.put("Колумбия", "col");
		tmp.put("Коморские острова", "com");
		tmp.put("Кабо Верде", "cpv");
		tmp.put("Коста-Рика", "crc");
		tmp.put("Хорватия", "cro");
		tmp.put("ЦАР", "cta");
		tmp.put("Куба", "cub");
		tmp.put("Кипр", "cyp");
		tmp.put("Чехия", "cze");
		tmp.put("Дания", "den");
		tmp.put("Джибути", "dji");
		tmp.put("Доминика", "dma");
		tmp.put("Доминиканская Республика", "dom");
		tmp.put("Эквадор", "ecu");
		tmp.put("Египет", "egy");
		tmp.put("Англия", "eng");
		tmp.put("Экваториальная Гвинея", "eqg");
		tmp.put("Эритрея", "eri");
		tmp.put("Испания", "esp");
		tmp.put("Эстония", "est");
		tmp.put("Эфиопия", "eth");
		tmp.put("Фиджи", "fij");
		tmp.put("Финляндия", "fin");
		tmp.put("Франция", "fra");
		tmp.put("Микронезия", "fsm");
		tmp.put("Габон", "gab");
		tmp.put("Гамбия", "gam");
		tmp.put("Грузия", "geo");
		tmp.put("Германия", "ger");
		tmp.put("Гана", "gha");
		tmp.put("Гвинея-Бисау", "gnb");
		tmp.put("Гваделупа", "gpe");
		tmp.put("Греция", "gre");
		tmp.put("Гренада", "grn");
		tmp.put("Гватемала", "gua");
		tmp.put("Гвинея", "gui");
		tmp.put("Гуам", "gum");
		tmp.put("Гайана", "guy");
		tmp.put("Гаити", "hai");
		tmp.put("Гонконг", "hkg");
		tmp.put("Гондурас", "hon");
		tmp.put("Венгрия", "hun");
		tmp.put("Индонезия", "idn");
		tmp.put("Индия", "ind");
		tmp.put("Ирландия", "irl");
		tmp.put("Иран", "irn");
		tmp.put("Ирак", "irq");
		tmp.put("Исландия", "isl");
		tmp.put("Израиль", "isr");
		tmp.put("Италия", "ita");
		tmp.put("Ямайка", "jam");
		tmp.put("Иордания", "jor");
		tmp.put("Япония", "jpn");
		tmp.put("Казахстан", "kaz");
		tmp.put("Кения", "ken");
		tmp.put("Киргизия", "kgz");
		tmp.put("Южная Корея", "kor");
		tmp.put("Саудовская Аравия", "ksa");
		tmp.put("Кувейт", "kuw");
		tmp.put("Лаос", "lao");
		tmp.put("Либерия", "lbr");
		tmp.put("Ливия", "lby");
		tmp.put("Санта Люсия", "lca");
		tmp.put("Лесото", "les");
		tmp.put("Ливан", "lib");
		tmp.put("Лихтенштейн", "lie");
		tmp.put("Литва", "ltu");
		tmp.put("Люксембург", "lux");
		tmp.put("Латвия", "lva");
		tmp.put("Макао", "mac");
		tmp.put("Мадагаскар", "mad");
		tmp.put("Марокко", "mar");
		tmp.put("Малайзия", "mas");
		tmp.put("Молдова", "mda");
		tmp.put("Мальдивы", "mdv");
		tmp.put("Мексика", "mex");
		tmp.put("Македония", "mkd");
		tmp.put("Мали", "mli");
		tmp.put("Мальта", "mlt");
		tmp.put("Черногория", "mne");
		tmp.put("Монголия", "mng");
		tmp.put("Мозамбик", "moz");
		tmp.put("Маврикий", "mri");
		tmp.put("Монсеррат", "msr");
		tmp.put("Мавритания", "mtn");
		tmp.put("Мартиника", "mtq");
		tmp.put("Малави", "mwi");
		tmp.put("Мьянма", "mya");
		tmp.put("Намибия", "nam");
		tmp.put("Никарагуа", "nca");
		tmp.put("Новая Каледония", "ncl");
		tmp.put("Голландия", "ned");
		tmp.put("Непал", "nep");
		tmp.put("Нигерия", "nga");
		tmp.put("Нигер", "nig");
		tmp.put("Норвегия", "nor");
		tmp.put("нет", "not");
		tmp.put("Новая Зеландия", "nzl");
		tmp.put("Оман", "oma");
		tmp.put("Пакистан", "pak");
		tmp.put("Панама", "pan");
		tmp.put("Парагвай", "par");
		tmp.put("Перу", "per");
		tmp.put("Филиппины", "phi");
		tmp.put("Палестина", "ple");
		tmp.put("Палау", "plw");
		tmp.put("Папуа-Новая Гвинея", "png");
		tmp.put("Польша", "pol");
		tmp.put("Португалия", "por");
		tmp.put("КНДР", "prk");
		tmp.put("Пуэрто-Рико", "pur");
		tmp.put("Катар", "qat");
		tmp.put("Реюнион", "reu");
		tmp.put("Румыния", "rou");
		tmp.put("ЮАР", "rsa");
		tmp.put("Россия", "rus");
		tmp.put("Руанда", "rwa");
		tmp.put("Самоа", "sam");
		tmp.put("Шотландия", "sco");
		tmp.put("Сенегал", "sen");
		tmp.put("Сейшелы", "sey");
		tmp.put("Сингапур", "sin");
		tmp.put("Сьерра-Леоне", "sle");
		tmp.put("Сальвадор", "slv");
		tmp.put("Соломоновы острова", "sol");
		tmp.put("Сомали", "som");
		tmp.put("Сербия", "srb");
		tmp.put("Шри-Ланка", "sri");
		tmp.put("Сан-Томе и Принсипи", "stp");
		tmp.put("Судан", "sud");
		tmp.put("Швейцария", "sui");
		tmp.put("Суринам", "sur");
		tmp.put("Словакия", "svk");
		tmp.put("Словения", "svn");
		tmp.put("Швеция", "swe");
		tmp.put("Свазиленд", "swz");
		tmp.put("Сирия", "syr");
		tmp.put("Таити", "tah");
		tmp.put("Тайвань", "tai");
		tmp.put("Танзания", "tan");
		tmp.put("Туркс и Кейкос", "tca");
		tmp.put("Тонга", "tga");
		tmp.put("Таиланд", "tha");
		tmp.put("Таджикистан", "tjk");
		tmp.put("Туркменистан", "tkm");
		tmp.put("Восточный Тимор", "tls");
		tmp.put("Того", "tog");
		tmp.put("Тринидад и Тобаго", "tri");
		tmp.put("Тунис", "tun");
		tmp.put("Турция", "tur");
		tmp.put("Тувалу", "tuv");
		tmp.put("ОАЭ", "uae");
		tmp.put("Уганда", "uga");
		tmp.put("Украина", "ukr");
		tmp.put("Уругвай", "uru");
		tmp.put("США", "usa");
		tmp.put("Узбекистан", "uzb");
		tmp.put("Вануату", "van");
		tmp.put("Венесуэла", "ven");
		tmp.put("Британские Виргинские острова", "vgb");
		tmp.put("Вьетнам", "vie");
		tmp.put("Американские Виргинские острова", "vir");
		tmp.put("Уэльс", "wal");
		tmp.put("Йемен", "yem");
		tmp.put("Замбия", "zam");
		tmp.put("Зимбабве", "zim");
		tmp.put("Амер. Самоа", "asa");
		tmp.put("Босния", "bih");
		tmp.put("Фареры", "fro");
		tmp.put("Сев. Ирландия", "nir");
		tmp.put("Сент-Китс", "skn");
		tmp.put("Сан Марино", "smr");
		tmp.put("Св. Винсент и Гренадины", "vin");
		nationalities = Collections.unmodifiableMap(tmp);
	}
}
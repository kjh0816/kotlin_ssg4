class BoardRepository {
    fun isUsableName(name: String): Boolean {
        for(board in boards){
            if(board.name == name){
                return false
            }
        }
        return true
    }

    fun isUsableCode(code: String): Boolean {
        for(board in boards){
            if(board.code == code){
                return false
            }
        }
        return true
    }

    fun add(memberId: Int, name: String, code: String) {

        val id = ++boardLastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        boards.add(Board(id, regDate, updateDate, memberId, name, code))


    }

    fun getBoardById(id: Int): Board? {
        for(board in boards){
            if(board.id == id){
                return board
            }
        }
        return null
    }

    fun delete(board: Board) {
        boards.remove(board)
    }

    fun modify(board: Board, name: String, code: String) {

        board.updateDate = Util.getNowDateStr()
        board.name = name
        board.code = code
    }

    fun getList() {
        println("번호 / 게시판 이름 / 게시판 코드 / 작성자 / 생성날짜")
        for(board in boards){
            val member = memberRepository.getMemberById(board.memberId)
            println("${board.id} / ${board.name} / ${board.code} / ${member!!.nickname} / ${board.regDate}")
        }
    }

    fun getBoardIdByCode(boardCode: String): Int {
        for(board in boards){
            if(board.code == boardCode){
                return board.id
            }
        }
        return 0
    }

    private var boardLastId = 0
    private val boards = mutableListOf<Board>()
}
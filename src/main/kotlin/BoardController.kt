class BoardController {
    fun add(req: Req) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요.")
            return
        }

        println("게시판 이름을 입력해주세요.")
        val name = readLineTrim()
        val isUsableName = boardRepository.isUsableName(name)
        if(!isUsableName){
            println("${name}는(은) 이미 존재하는 게시판 이름입니다.")
            return
        }
        println("게시판 코드를 입력해주세요.")
        val code = readLineTrim()
        val isUsableCode = boardRepository.isUsableCode(code)
        if(!isUsableCode){
            println("${code}는(은) 이미 존재하는 게시판 코드입니다.")
            return
        }

        boardRepository.add(loginedMember!!.id, name, code)
        println("$name 게시판이 추가되었습니다.")


    }

    fun delete(req: Req) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요.")
        }
        val id = req.getIntParam("id", 0)
        if(id == 0){
            println("$id 파라미터를 숫자로 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(id)
        if(board == null){
            println("${id}번 게시판이 존재하지 않습니다.")
            return
        }
        if(board.memberId != loginedMember!!.id){
            println("권한이 없습니다.")
            return
        }

        boardRepository.delete(board)
        println("${id}번 게시판이 삭제되었습니다.")

    }

    fun modify(req: Req) {
        if(loginedMember == null){
            println("로그인 후 이용해주세요.")
        }
        val id = req.getIntParam("id", 0)
        if(id == 0){
            println("$id 파라미터를 숫자로 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(id)
        if(board == null){
            println("${id}번 게시판이 존재하지 않습니다.")
            return
        }
        if(board.memberId != loginedMember!!.id){
            println("권한이 없습니다.")
            return
        }

        println("새 게시판 이름을 입력해주세요.")
        val name = readLineTrim()
        val isUsableName = boardRepository.isUsableName(name)
        if(!isUsableName){
            println("${name}는(은) 이미 존재하는 게시판 이름입니다.")
            return
        }
        println("새 게시판 코드를 입력해주세요.")
        val code = readLineTrim()
        val isUsableCode = boardRepository.isUsableCode(code)
        if(!isUsableCode){
            println("${code}는(은) 이미 존재하는 게시판 코드입니다.")
            return
        }


        boardRepository.modify(board, name, code)
        println("${id}번 게시판이 수정되었습니다.")

    }

    fun detail(req: Req) {
        val id = req.getIntParam("id", 0)
        if(id == 0){
            println("$id 파라미터를 숫자로 입력해주세요.")
            return
        }
        val board = boardRepository.getBoardById(id)
        if(board == null){
            println("${id}번 게시판이 존재하지 않습니다.")
            return
        }

        val member = memberRepository.getMemberById(board.memberId)
        println("번호: ${board.id}")
        println("게시판 이름: ${board.name}")
        println("게시판 코드: ${board.code}")
        println("작성자: ${member!!.nickname}")
        println("생성날짜: ${board.regDate}")
        println("수정날짜: ${board.updateDate}")


    }

    fun list(req: Req) {

        boardRepository.getList()



    }
}
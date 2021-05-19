class Req(command: String){

    val paramMap: Map<String, String>
    val actionPath: String

    init {


        val commandBits = command.split("?", limit=2)

        actionPath = commandBits[0].trim()


        // /board/list?searchKeyword=ee&page=1

        val queryStr = if(commandBits.lastIndex == 1 && commandBits[1].isNotEmpty()){
            commandBits[1].trim()
        }else{
            ""
        }

        paramMap = if(queryStr.isEmpty()){
            mapOf()
        }else{
            val queryStrBits = queryStr.split("&")
            val paramMapTemp = mutableMapOf<String, String>()
            for(queryStrBit in queryStrBits){

                val eachQueryBits = queryStrBit.split("=", limit=2)
                val key = queryStrBits[0].trim()
                val value = if(eachQueryBits.lastIndex == 1 && eachQueryBits[1].isNotEmpty()){
                    queryStrBits[1].trim()
                }else{
                    ""
                }

                if(value.isNotEmpty()){
                    paramMapTemp[key] = value
                }




            }
            paramMapTemp
        }


    }



    fun getStrParam(name: String, default: String): String{
        return paramMap[name]?: default
    }

    fun getIntParam(name: String, default: Int): Int{
        return if(paramMap[name] != null){
            try{
                paramMap[name]!!.toInt()
            }catch(e: NumberFormatException){
                default
            }
        }else{
            default
        }

    }



}
package com.example.muhendisliktasarimi.domain.use_case

//class GetWordsUseCase @Inject constructor(
//    private val repo: WordsRepo
//) {
//
//    fun executeGetWords(search: String): Flow<Resource<DictionaryResponseDto>> = flow {
//        try {
//            emit(Resource.Loading())
//            val words = repo.getWords(search)
//            if (words != null){
//                emit(Resource.Success(words))
//            }else{
//                println("No data")
//                emit(Resource.Error("No data"))
//            }
//        }catch (e: IOError){
//            println("IOError")
//            emit(Resource.Error(message = "No internet connection"))
//
//        }catch (e: HttpException){
//            println("HttpException")
//            emit(Resource.Error(message = "Error"))
//
//        }catch (e: Exception){
//            println(e.localizedMessage)
//            println(e.message)
//            emit(Resource.Error(message = "No internet connection"))
//        }
//    }
//
//}
import com.example.muhendisliktasarimi.data.remote.dto.search_words.Def
import com.example.muhendisliktasarimi.data.remote.dto.search_words.Head


data class DictionaryResponse(
  val head: Head,
  val def: List<Def>
)

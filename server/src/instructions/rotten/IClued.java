package instructions.rotten;

/**
 * Обобщение для т.н. "сырых"
 * комманд, которые имеют дополнительный
 * (ключевой) параметр, который нужно учесть
 * при создании точной команды
 * @param <K> ключ элементов коллекции
 */
public interface IClued {
  /**
   * Свойство взятие ключа
   * @return ключ
   */
  Integer Key();
}

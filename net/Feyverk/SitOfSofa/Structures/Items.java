/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.Feyverk.SitOfSofa.Structures;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Пётр id|chance|data|magic|min_count|max_count|
 */
public class Items
{

    /**
     * Стак предметов
     */
    private ItemStack Item;
    private int min_count = 1;
    private int max_count = 1;

    /**
     * Получает колличество предметов в стаке
     *
     * @return Получает колличество предметов в стаке
     */
    public int getCount()
    {
        return this.Item.getAmount();
    }

    /**
     * Получает дополнительный байт у предмета
     *
     * @return Получает дополнительный байт у предмета
     */
    public byte getData()
    {
        return getItem().getData().getData();
    }

    /**
     * Получает информацию о магии у данного предмета
     *
     * @return Получает информацию о магии у данного предмета
     */
    public Map<Enchantment, Integer> getMagic()
    {
        return getItem().getEnchantments();
    }

    /**
     * Получает максимальное количество предметов данного типа которое
     * пользователь может найти за раз. Если максимум меньше минимума то
     * автоматически присваеваевается равным
     *
     * @return Получает максимальное количество предметов данного типа
     */
    public int getMax_count()
    {
        if (this.max_count < this.min_count)
        {
            this.max_count = this.min_count;
        }
        if (this.max_count < 0)
        {
            this.max_count = 0;
        }
        return this.max_count;
    }

    /**
     * Получает минимальное количество предметов данного типа которое
     * пользователь может найти за раз. Если минимум больше максимума то
     * автоматически присваеваевается равным
     *
     * @return Получает минимальное количество предметов данного типа
     */
    public int getMin_count()
    {
        if (this.min_count > this.max_count)
        {
            this.min_count = this.max_count;
        }
        if (this.min_count < 0)
        {
            this.min_count = 0;
        }
        return this.min_count;
    }

    /**
     * Стак предметов с указанными свойствами
     *
     * @return Возвращает Стак предметов с указанными свойствами
     */
    public ItemStack getItem()
    {
        if (this.Item == null)
        {
            this.Item = new ItemStack(0);
        }
        return this.Item;
    }

    /**
     * Задаёт максимальное количество предметов которое может найти чел в стаке.
     *
     * @param max_count Максимальное количество предметов от 1 до 64
     */
    public final void setMax_count(int max_count)
    {
        if (max_count > 0)
        {
            this.max_count = max_count;
        } else
        {
            this.max_count = 1;
        }
        reGenerateCount();
    }

    /**
     * Задаёт минимальное количество предметов которое может найти чел в стаке.
     *
     * @param max_count Минимальное количество предметов от 1 до 64
     */
    public final void setMin_count(int min_count)
    {
        if (min_count > 0)
        {
            this.min_count = min_count;
        } else
        {
            this.min_count = 1;
        }
        reGenerateCount();
    }

    /**
     * повторно регенерирует количество предметов
     */
    public int reGenerateCount()
    {
        Random rand = new Random();
        Integer count = 1;
        if (getMax_count() - getMin_count() > 0)
        {
            count = getMin_count() + rand.nextInt(getMax_count() - getMin_count());
        }
        this.Item.setAmount(count);
        return count;
    }

    /**
     * Задаёт дополнительное свойство предмета
     *
     * @param data байт определяющий дополнительную характеристику
     */
    public final void setData(byte data)
    {
        this.Item.getData().setData(data);
    }

    /**
     * Задаёт магические свойства предмету
     *
     * @param magic Списко магических свойств и уровней у предметов
     */
    public final void setEnchantment(Map<Enchantment, Integer> magic)
    {
        try
        {
            if (magic.size() > 0)
            {
                getItem().addEnchantments(magic);
            }
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] in setEnchantment {0} StackTrace: {1}", new Object[]
            {
                e.getMessage(), e.getStackTrace().toString()
            });
        }
    }

    @Override
    public int hashCode()
    {
        return Item.hashCode() + min_count + max_count - (max_count * min_count);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Items other = (Items) obj;
        if (!Objects.equals(this.Item, other.Item))
        {
            return false;
        }
        if (this.min_count != other.min_count)
        {
            return false;
        }
        if (this.max_count != other.max_count)
        {
            return false;
        }
        return true;
    }

    /**
     * Конструктор инициализирующий значениями объект класса Items
     *
     * @param Item ID предмета
     * @param min_count Максимальное количество в стаке
     * @param max_count Минимальное колличество в стаке
     * @param data Дополнительные байты у предмета (цвет итд)
     * @param magic Карта магия и сила магии
     */
    public Items(int Item, int min_count, int max_count, byte data, Map<Enchantment, Integer> magic)
    {
        this.Item = new ItemStack(Item);
        setMax_count(max_count);
        setMin_count(min_count);
        setData(data);
        if (magic != null)
        {
            setEnchantment(magic);
        }
    }

    @Override
    public String toString()
    {
        String _Temp = "";
        _Temp += getItem().getTypeId() + ": %chance% | " + getMin_count() + " | " + getMax_count() + " | " + getData();
        if (getMagic().size() == 0)
        {
            _Temp += " | no";
        } else
        {
            _Temp += "{";
            for (Map.Entry<Enchantment, Integer> entry : getMagic().entrySet())
            {
                _Temp += "{" + entry.getKey().getName() + " , " + entry.getValue() + "},";
            }
            _Temp = _Temp.substring(0, _Temp.length() - 1);
            _Temp += "}";
        }
        return _Temp; //To change body of generated methods, choose Tools | Templates.
    }
    private static final Logger LOG = Logger.getLogger(Items.class.getName());
}

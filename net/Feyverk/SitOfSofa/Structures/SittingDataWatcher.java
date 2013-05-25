/**
 * Описывает пакет данных для отправки
 */
package net.Feyverk.SitOfSofa.Structures;
import java.util.ArrayList;
import java.util.logging.Logger;
/*
import net.minecraft.server.v1_5_R3.DataWatcher;
import net.minecraft.server.v1_5_R3.WatchableObject;
//*/
//*
import net.minecraft.server.v1_5_R2.DataWatcher;
import net.minecraft.server.v1_5_R2.WatchableObject;
/*/
/*
import net.minecraft.server.v1_4_R1.DataWatcher;
import net.minecraft.server.v1_4_R1.WatchableObject;
//*/

public class SittingDataWatcher extends DataWatcher {

	private byte metadata;
	
	public SittingDataWatcher(byte metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public ArrayList<WatchableObject> b() 
        {
		ArrayList<WatchableObject> list = new ArrayList<>();
		WatchableObject wo = new WatchableObject(0, 0, metadata);
		list.add(wo);
		return list;
	}
    private static final Logger LOG = Logger.getLogger(SittingDataWatcher.class.getName());
	
}

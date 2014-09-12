package dustmod.inscriptions;

import dustmod.DustMod;
import dustmod.items.ItemInscription;
import dustmod.items.ItemWornInscription;
import dustmod.runes.RuneEvent;
import dustmod.runes.EntityRune;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;

public class InscriptionEvent {

	public int[][] referenceDesign;
	public int id;
	public String idName = "";
	public String properName = "";
	
	public String description = "";
	public String notes = ""; //for sacrifices and stuff
	public String author = "";
	
	public boolean isRemote = false;

	public boolean secret = false;
	public String permission = "ALL";
	
	 //Set by the coder, unalterable by user configs. 
	//Setting it to false disallows all use, but if true it will not affect anything
	//This is for the case where something breaks, it gives the coder time to fix it later 
	public boolean permaAllowed = true;
	
	
	public InscriptionEvent(int[][] design, String idName, String properName, int id){
		if(design.length > 16 || design[0].length > 16){
			throw new IllegalArgumentException("Inscription dimensions too big! " + idName + " Max:16x16");
		}
		this.referenceDesign = design; 
		this.id = id;
		this.idName = idName;
		this.properName = properName;
	}
	
	public boolean canPlayerKnowInscription(EntityPlayer player){
//		if(player != null && player.username.toLowerCase().equals("billytg101")){
//			return true;
//		}else if(true) return false;
		
		// XXX WTH?
		boolean isOP = true;
		try{
			isOP = MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
		}catch(Exception e){}
    	return permaAllowed && (this.permission.equals("ALL") || (this.permission.equals("OP") && isOP));
    }
	
	public String getDescription(){
		return description;
	}
	public String getNotes(){
		return notes;
	}
	public String getIDName(){
		return idName;
	}
	public String getInscriptionName(){
		return properName;
	}
	public String getAuthor(){
		return author;
	}
	

	
	public void setDescription(String desc){
		this.description = desc;
	}
	public void setNotes(String notes){
		this.notes = notes;
	}
	public void setIDName(String idName){
		this.idName = idName;
	}
	public void setInscriptionName(String name){
		this.properName = name;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	
	/**
	 * Called when this inscription is charged with a charging rune. 
	 * It is the responsibility of this function to set the new damage level of the 
	 * inscription item. 0 being full power, 999 being empty. The power level to 
	 * begin with will be 999.
	 * 
	 * @param rune	The DustEvent used to check sacrifices
	 * @param e		The EntityDust used to get items and nearby player things 
	 * @param item	The inscription item.
	 * @return 		True if charging of the inscription succeeded. False otherwise.
	 */
	public boolean callSacrifice(RuneEvent rune, EntityRune e, ItemStack item){
		return false;
	}
	
	public void onUpdate(EntityLivingBase wearer, ItemStack item, boolean[] buttons){
	}
	
	public void onDamage(EntityLivingBase wearer, ItemStack item, DamageSource source, int damage){}
	
	public int getPreventedDamage(EntityLivingBase wearer, ItemStack item, DamageSource source, int damage){return damage;}
	
	public int getArmorPoints(EntityLivingBase wearer, ItemStack item){
		return 0;
	}
	
	public void onRemoval(EntityLivingBase wearer, ItemStack item){
	}
	
	public void onEquip(EntityLivingBase wearer, ItemStack item){
	}
	
	public void onCreate(EntityLivingBase creator, ItemStack item){
	}
	
	/**
	 * Called when the item right-clicked while in hand
	 * @param player
	 * @param item
	 */
	public ItemStack onItemRightClick(EntityPlayer player, ItemStack item){
		return item;
	}
	
	/**
	 * Called when the player picks up an item in the world
	 * returns the mutated item to be put in the inventory
	 * @param wearer
	 * @param pickedup
	 * @return
	 */
	public ItemStack onItemPickup(EntityLivingBase wearer, ItemStack insc, ItemStack pickedup){return pickedup;}
	
	public void damage(EntityPlayer ent, ItemStack item, int amt){
		int curDamage = item.getItemDamage();
		if(curDamage + amt > item.getMaxDamage()-1){
			amt = (item.getMaxDamage()-curDamage) -1;
		}
		item.damageItem(amt, ent);
		if(item.getItemDamage() >= ItemWornInscription.max-1){
			DustMod.sendRenderBreakItem(ent, item);
		}
		if(item.stackSize <= 0){
			item.stackSize = 1;
			item.setItemDamage(ItemInscription.max);
//			ent.inventory.armorInventory[2] = null;
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " InscriptionID:[" + this.idName + ":" + this.id + "]";
	}
}
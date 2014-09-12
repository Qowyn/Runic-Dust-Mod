/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dustmod.defaults.runes;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import dustmod.runes.RuneEvent;
import dustmod.runes.EntityRune;

/**
 *
 * @author billythegoat101
 */
public class REBounce extends RuneEvent
{
    public REBounce()
    {
        super();
    }
	
	@Override
    public void initGraphics(EntityRune e){
    	super.initGraphics(e);
    	
    	
    }

    public void onInit(EntityRune e)
    {
        ItemStack[] req = new ItemStack[]
        {
            new ItemStack(Items.slime_ball, 4, -1)
        };
        sacrifice(e, req);

        if (req[0].stackSize > 0)
        {
            e.fizzle();
            return;
        }

//        e.renderFlamesDust = true;
    }

    public void onTick(EntityRune e)
    {
        List<Entity> entities = this.getEntities(e, 0.35D);
//        System.out.println("Dicks " + entities.size());
        for (Entity i: entities)
        {
            if (i instanceof EntityLivingBase)
            {
                EntityLivingBase el = (EntityLivingBase)i;
                double cons = 0;//0.0784000015258789;
                double yVel = i.motionY + cons;
                double diff = e.posY - el.prevPosY;
//                System.out.println("i.motion " + mod_DustMod.getMoveForward(el) + " " + mod_DustMod.getMoveSpeed(el));
                // TODO isJumping
                if (!el.onGround /*&& !el.isJumping*/ && yVel < 0.7D)
                {
                    el.setJumping(true);
//                    el.setMoveForward(mod_DustMod.getMoveSpeed(el)*2);
//                    i.motionY = 1D;
//                    i.motionX *= 2;
//                    i.motionZ *= 2;
                    i.addVelocity(0, 1.27D, 0);
//                    System.out.println("Launch " + " " + (i.motionY + cons) + " " + i.motionX + " " + i.motionZ);
                    i.velocityChanged = true;
                }
                if (!el.onGround /*i.motionY < 0*/)
                {
                    i.fallDistance = 0;
                }else{
                    el.setJumping(false);
                }
            }
        }
        entities = this.getEntities(e, 3D);

        for (Entity i: entities)
        {
            if (i instanceof EntityLivingBase)
            {
            	EntityLivingBase el = (EntityLivingBase)i;
                if (!el.onGround /*i.motionY < 0*/)
                {
                    i.fallDistance = 0;
                }else{
                    el.setJumping(false);
                }
            }
        }
    }
}
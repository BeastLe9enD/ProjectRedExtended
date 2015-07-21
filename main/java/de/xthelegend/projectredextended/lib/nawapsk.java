package de.xthelegend.projectredextended.lib;



import codechicken.lib.vec.Vector3;

public class nawapsk
{
    int fep = -1;
    pqmwaqf ray;
    Vector3 org;
    Vector3 dest;
    Vector3 fracs;
    Vector3 frace;
    long seed;

    public nawapsk(Vector3 var1, Vector3 var2, long var3)
    {
        this.org = new Vector3(var1);
        this.dest = new Vector3(var2);
        this.fracs = new Vector3(var1);
        this.frace = new Vector3();
        this.seed = var3;
        xawpnqf.fillVector(this.frace, this.org, this.dest, 0.125F, this.seed);
        this.ray = new pqmwaqf(this.fracs, this.frace);
    }

    public boolean iterate()
    {
        if (this.fep == -1)
        {
            ++this.fep;
            return true;
        }
        else if (!this.ray.step())
        {
            return true;
        }
        else if (this.fep == 8)
        {
            return false;
        }
        else
        {
            this.fracs.set(this.frace);
            xawpnqf.fillVector(this.frace, this.org, this.dest, (float)this.fep / 8.0F, this.seed);
            this.ray.set(this.fracs, this.frace);
            ++this.fep;
            return true;
        }
    }

    public Vector3 get()
    {
        return new Vector3((double)this.ray.xp, (double)this.ray.yp, (double)this.ray.zp);
    }
}
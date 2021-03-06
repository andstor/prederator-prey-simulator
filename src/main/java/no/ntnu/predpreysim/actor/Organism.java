package no.ntnu.predpreysim.actor;

import no.ntnu.predpreysim.Field;
import no.ntnu.predpreysim.Location;
import no.ntnu.predpreysim.Randomizer;

import java.util.List;
import java.util.Random;

public abstract class Organism implements Actor {

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The allowed layer location.
    private int layer = 1;
    // The animal's age.
    private int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;


    /**
     * Create a new animal at location in field.
     * An animal can have an initial age of zero or a
     * random age.
     *
     * @param randomAge If true, the animal will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Organism(boolean randomAge, Field field, Location location) {
        if (randomAge) {
            age = rand.nextInt(getMaxAge());
        } else {
            age = 0;
        }
        alive = true;
        this.field = field;
        setLocation(location);
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     *
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newAnimals);

    /**
     * Return the maximum age for a specific type of animal.
     *
     * @return The animal's maximum age.
     */
    abstract public int getMaxAge();

    /**
     * Return the animal's age.
     *
     * @return The animal's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Create a new organism. An animal may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the animal will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    abstract protected Organism createOrganism(boolean randomAge,
                                             Field field, Location location);

    /**
     * Is the actor still active? This will be the case if the
     * animal is still alive.
     *
     * @return true if the actor is active, false otherwise.
     */
    public boolean isActive() {
        return isAlive();
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        this.alive = false;
        if (this.location != null) {
            getField().clear(this.location);
            this.location = null;
            this.field = null;
        }
    }



    /**
     * Return the animal's location.
     *
     * @return The animal's location.
     */
    protected Location getLocation() {
        return this.location;
    }

    /**
     * Place the animal at the new location in the given field.
     *
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Increase the age. This could result in the animal's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Return the animal's field.
     *
     * @return The animal's field.
     */
    protected Field getField() {
        return this.field;
    }

    @Override
    public int getLayerValue() {
        return this.layer;
    }

}

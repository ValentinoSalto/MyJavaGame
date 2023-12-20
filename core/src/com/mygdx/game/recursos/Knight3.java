package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Knight3 {

    public enum EstadoPersonaje {
        IDLE,
        WALKING_LEFT,
        WALKING_RIGHT,
        JUMP,
        RUN_RIGHT,
        RUN_LEFT,
        ATTACK,
        COVER,
        HURT
    }

    private Sprite spr;
    private float alto, ancho;
    private float x, y;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkingLeftAnimation;
    private Animation<TextureRegion> walkingRightAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> runLeftAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> coverAnimation;
    private TextureRegion[] regionsMovement_idle;
    private TextureRegion[] regionsMovement_walking_left;
    private TextureRegion[] regionsMovement_walking_right;
    private TextureRegion[] regionsMovement_run;
    private TextureRegion[] regionsMovement_runLeft;
    private TextureRegion[] regionsMovement_attack;
    private TextureRegion[] regionsMovement_cover;
    private float time;
    private TextureRegion currentFrame;

    private boolean ataqueIniciado;
    private float tiempoAtaque;
    private EstadoPersonaje estadoActual;

    public Knight3(float x, float y, float ancho, float alto) {
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho; 

        Texture idleTexture = new Texture(Gdx.files.internal("Personajes/Hero/Combat Ready Idle.png"));
        Texture walkingRightTexture = new Texture(Gdx.files.internal("Personajes/Hero/Walk.png"));
        Texture walkingLeftTexture = new Texture(Gdx.files.internal("Personajes/Hero/Walk2.png")); 
        Texture runTexture = new Texture(Gdx.files.internal("Personajes/Hero/Run.png")); 
        Texture runLeftTexture = new Texture(Gdx.files.internal("Personajes/Hero/Run2.png"));
        Texture attackTexture = new Texture(Gdx.files.internal("Personajes/Hero/Attack 1.png"));
        Texture coverTexture = new Texture(Gdx.files.internal("Personajes/Hero/Shield Raise.png"));

        TextureRegion[][] idleFrames = TextureRegion.split(idleTexture, idleTexture.getWidth() / 5, idleTexture.getHeight());
        regionsMovement_idle = new TextureRegion[5];
        
        TextureRegion[][] walkingLeftFrames = TextureRegion.split(walkingLeftTexture, walkingLeftTexture.getWidth() / 6, walkingLeftTexture.getHeight());
        regionsMovement_walking_left = new TextureRegion[6];
        
        TextureRegion[][] walkingRightFrames = TextureRegion.split(walkingRightTexture, walkingRightTexture.getWidth() / 6, walkingRightTexture.getHeight());
        regionsMovement_walking_right = new TextureRegion[6];
        
        TextureRegion[][] runFrames = TextureRegion.split(runTexture,runTexture.getWidth() / 6, runTexture.getHeight());
        regionsMovement_run = new TextureRegion[6];
        
        TextureRegion[][] runLeftFrames = TextureRegion.split(runLeftTexture,runLeftTexture.getWidth() / 6, runLeftTexture.getHeight());
        regionsMovement_runLeft = new TextureRegion[6];
        
        TextureRegion[][] attackFrames = TextureRegion.split(attackTexture, attackTexture.getWidth() / 10, attackTexture.getHeight());
        regionsMovement_attack = new TextureRegion[10];
        
        TextureRegion[][] coverFrames = TextureRegion.split(coverTexture, coverTexture.getWidth() / 5,  coverTexture.getHeight());
        regionsMovement_cover = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {
            regionsMovement_idle[i] = idleFrames[0][i];
            idleAnimation = new Animation<>(1 / 5f, idleFrames[0]);
            time = 0f;
        }

        for (int i = 0; i < 6; i++) {
            regionsMovement_walking_left[i] = walkingLeftFrames[0][i];
            walkingLeftAnimation = new Animation<>(1 / 10f, walkingLeftFrames[0]);
            time = 0f;
        }

        for (int i = 0; i < 6; i++) {
            regionsMovement_walking_right[i] = walkingRightFrames[0][i];
            walkingRightAnimation = new Animation<>(1 / 10f, walkingRightFrames[0]);
            time = 0f;
        }
        
        for (int i = 0; i < 6; i++) {
            regionsMovement_run[i] = runFrames[0][i];
            runAnimation = new Animation<>(1 / 10f, runFrames[0]);
            time = 0f;
        }
        
        for (int i = 0; i < 6; i++) {
            regionsMovement_runLeft[i] = runLeftFrames[0][i];
            runLeftAnimation = new Animation<>(1 / 10f, runLeftFrames[0]);
            time = 0f;
        }

        for (int i = 0; i < 10; i++) {
            regionsMovement_attack[i] = attackFrames[0][i];
            attackAnimation = new Animation<>(1 / 20f, attackFrames[0]);
            time = 0f;
        }

        for (int i = 0; i < 5; i++) {
            regionsMovement_cover[i] = coverFrames[0][i];
            coverAnimation = new Animation<>(1 / 10f, coverFrames[0]);
            time = 0f;
        }

        estadoActual = EstadoPersonaje.IDLE;

        spr = new Sprite(idleAnimation.getKeyFrame(0, true));
        spr.setPosition(x, y);
        spr.setSize(ancho, alto);
    }

    public void render(SpriteBatch batch) {
        time += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) idleAnimation.getKeyFrame(time, true);
        spr.draw(batch);
        float X = spr.getX();
        float ANCHO = spr.getWidth();
    }

    public void updateAnimation(float delta) {
        switch (estadoActual) {
            case IDLE:
                spr.setRegion(idleAnimation.getKeyFrame(time, true));
                break;
                
            case WALKING_LEFT:
                spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
                x -= 3;
                if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                	spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
                	cambiarEstado(Knight3.EstadoPersonaje.RUN_LEFT);
                	x-=6;
                	
                }
                break;
                
            case WALKING_RIGHT:
                spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));
                x += 3;
                if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                	
                	spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
                	cambiarEstado(Knight3.EstadoPersonaje.RUN_RIGHT);
                	x+=6;
                	
                }
                break;
                
           /* case RUN_RIGHT:
                spr.setRegion(runAnimation.getKeyFrame(time, true));
                x += 6;
                break;
                
            case RUN_LEFT:
                spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
                x -= 6;
                break;
           */     
            case ATTACK:
                spr.setRegion(attackAnimation.getKeyFrame(time, true));
                break;
                
            case COVER:
                spr.setRegion(coverAnimation.getKeyFrame(time, true));
                break;
        }

        spr.setPosition(x, y);

        if (ataqueIniciado) {
            tiempoAtaque += delta;
            if (tiempoAtaque < attackAnimation.getAnimationDuration()) {
                spr.setRegion(attackAnimation.getKeyFrame(tiempoAtaque, false));
            } else {
                ataqueIniciado = false;
                tiempoAtaque = 0f;
                cambiarEstado(EstadoPersonaje.IDLE);
            }
        }
    }

    public void cambiarEstado(EstadoPersonaje nuevoEstado) {
        estadoActual = nuevoEstado;
        spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

        if (nuevoEstado == EstadoPersonaje.ATTACK) {
            iniciarAtaque();
        }
    }

    private void iniciarAtaque() {
        ataqueIniciado = true;
        tiempoAtaque = 0f;
        
    }

    private Animation<TextureRegion> getAnimationForCurrentState() {
        switch (estadoActual) {
            case IDLE:
                return idleAnimation;
            case WALKING_LEFT:
                return walkingLeftAnimation;
            case WALKING_RIGHT:
                return walkingRightAnimation;
            case RUN_RIGHT:
                return runAnimation;
            case RUN_LEFT:
                return runLeftAnimation;
            case ATTACK:
                return attackAnimation;
            case COVER:
                return coverAnimation;
            default:
                return idleAnimation;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return alto;
    }

    public float getHeight() {
        return ancho;
    }
}

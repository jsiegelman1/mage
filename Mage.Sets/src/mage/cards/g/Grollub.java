package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Grollub extends CardImpl {

    public Grollub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Grollub is dealt damage, each opponent gains that much life.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                Zone.BATTLEFIELD,
                new EachOpponentGainsLifeEffect(), false, false, true));

    }

    public Grollub(final Grollub card) {
        super(card);
    }

    @Override
    public Grollub copy() {
        return new Grollub(this);
    }
}

class EachOpponentGainsLifeEffect extends OneShotEffect {

    public EachOpponentGainsLifeEffect() {
        super(Outcome.Neutral);
        this.staticText = "each opponent gains that much life";
    }

    public EachOpponentGainsLifeEffect(final EachOpponentGainsLifeEffect effect) {
        super(effect);
    }

    @Override
    public EachOpponentGainsLifeEffect copy() {
        return new EachOpponentGainsLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                int amount = (Integer) getValue("damage");
                if (amount > 0) {
                    opponent.gainLife(amount, game, source);
                }
            }
        }
        return false;
    }
}

package com.carmaxfrontend.checkout;

import com.carmax.common.entity.CartItem;
import com.carmax.common.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {
	private static final int DIM_DIVISOR = 139;

	public CheckoutInfo prepareCheckout(List<CartItem> cartItems) {
		CheckoutInfo checkoutInfo = new CheckoutInfo();

		float productCost = calculateProductCost(cartItems);
		float productTotal = calculateProductTotal(cartItems);
		float shippingCostTotal = calculateShippingCost(cartItems);
		float paymentTotal = productTotal + shippingCostTotal;

		checkoutInfo.setProductCost(productCost);
		checkoutInfo.setProductTotal(productTotal);
		checkoutInfo.setShippingCostTotal(shippingCostTotal);
		checkoutInfo.setPaymentTotal(paymentTotal);

		return checkoutInfo;
	}

	private float calculateShippingCost(List<CartItem> cartItems) {
		float shippingCostTotal = 0.0f;

		for (CartItem item : cartItems) {
			Product product = item.getProduct();
			float dimWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
			float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;
			float shippingCost = finalWeight * item.getQuantity();

			item.setShippingCost(shippingCost);

			shippingCostTotal += shippingCost;
		}

		return shippingCostTotal;
	}

	private float calculateProductTotal(List<CartItem> cartItems) {
		float total = 0.0f;

		for (CartItem item : cartItems) {
			total += item.getSubtotal();
		}

		return total;
	}

	private float calculateProductCost(List<CartItem> cartItems) {
		float cost = 0.0f;

		for (CartItem item : cartItems) {
			cost += item.getQuantity() * item.getProduct().getCost();
		}

		return cost;
	}
}

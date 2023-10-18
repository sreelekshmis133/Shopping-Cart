package com.example.shoppingcart;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shoppingcart.MainActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.model.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductModel> productList;
    private Context context;

    public ProductAdapter(List<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public double calculateTotalAmount() {
        double totalAmount = 0;
        for (ProductModel productModel : productList) {
            totalAmount += productModel.getProductPrize();
        }
        return totalAmount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = productList.get(position);


        holder.productNameTextView.setText(product.getProductName());
        holder.productSizeTextView.setText("Size: " + product.getProductSize());
        holder.productQuantityTextView.setText("Quantity: " + product.getProductQuantity());
        holder.productPriceTextView.setText("Price: ₹ " + product.getProductPrize());

        if (product.isCouponApplied()) {
            holder.applyCouponCodeButton.setText("Remove");
            holder.couponEditText.setText("");
        } else {
            holder.applyCouponCodeButton.setText("Apply");
        }

        holder.applyCouponCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String couponCode = holder.couponEditText.getText().toString();
                if (product.isCouponApplied()) {
                    revertCouponChanges(holder, product);
                } else {
                    applyCoupon(holder, product);
                }
                holder.couponEditText.clearFocus();
            }
        });
    }

    private void applyCoupon(ViewHolder holder, ProductModel product) {
        String couponCode = holder.couponEditText.getText().toString();
        String discountRate = couponCode.replaceFirst(".*?(\\d+).*", "₹1");

        Log.i("discountRate", discountRate);

        double discount;
        try {
            discount = Double.parseDouble(discountRate);
            if (discount >= 0 && discount <= 100) {
                product.setOriginalProductPrize(product.getProductPrize());

                double discountedPrice = ((discount * product.getProductPrize()) / 100);
                product.setProductPrize(discountedPrice);
                product.setCouponApplied(true);
                notifyDataSetChanged();

                ((MainActivity) context).updateTotalAmount();
                holder.applyCouponCodeButton.setText("Remove");
            } else {
                Toast.makeText(context, "Invalid discount rate", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Invalid discount rate", Toast.LENGTH_SHORT).show();
        }
    }

    private void revertCouponChanges(ViewHolder holder, ProductModel product) {
        double originalProductPrice = product.getOriginalProductPrize();

        product.setProductPrize(originalProductPrice);
        product.setCouponApplied(false);
        notifyDataSetChanged();

        ((MainActivity) context).updateTotalAmount();
        holder.applyCouponCodeButton.setText("Apply");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImageView;
        public TextView productNameTextView;
        public TextView productSizeTextView;
        public TextView productQuantityTextView;
        public TextView productPriceTextView;
        public LinearLayout couponSection;
        public EditText couponEditText;
        public Button applyCouponCodeButton;

        private TextView couponValueTextView;

        public Button applyButton;
        private ImageView tagImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageView = itemView.findViewById(R.id.productImage);
            productNameTextView = itemView.findViewById(R.id.productName);
            productSizeTextView = itemView.findViewById(R.id.productSize);
            productQuantityTextView = itemView.findViewById(R.id.productQuantity);
            productPriceTextView = itemView.findViewById(R.id.productPrice);
            couponSection = itemView.findViewById(R.id.couponSection);
            couponEditText = itemView.findViewById(R.id.couponEditText);
            applyCouponCodeButton = itemView.findViewById(R.id.applyCouponCodeButton);
            tagImageView = itemView.findViewById(R.id.tagImage);
            applyButton = itemView.findViewById(R.id.applyCouponButton);

            applyButton.setOnClickListener(v -> {
                tagImageView.setVisibility(View.GONE);
                applyButton.setVisibility(View.GONE);
                couponSection.setVisibility(View.VISIBLE);
            });
        }
    }
}

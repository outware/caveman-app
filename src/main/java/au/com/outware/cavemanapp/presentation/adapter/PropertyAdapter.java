package au.com.outware.cavemanapp.presentation.adapter;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.R;
import au.com.outware.cavemanapp.data.model.ConfigurationProperty;
import au.com.outware.cavemanapp.util.SimpleTextWatcher;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    private static final int VIEW_TYPE_ENVIRONMENT_NAME = 0;
    private static final int VIEW_TYPE_STRING = 1;
    private static final int VIEW_TYPE_BOOLEAN = 2;
    private static final int VIEW_TYPE_NUMBER = 3;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.###");

    public final Environment environment;
    private final boolean setEnvironmentName;
    private final List<ConfigurationProperty> properties;

    public PropertyAdapter(Environment environment, List<ConfigurationProperty> properties, boolean setEnvironmentName) {
        this.environment = environment;
        this.properties = properties;
        this.setEnvironmentName = setEnvironmentName;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = -1;

        if (setEnvironmentName && position == 0) {
            viewType = VIEW_TYPE_ENVIRONMENT_NAME;
        } else {
            final Class propertyType = properties.get(position - (setEnvironmentName ? 1 : 0)).getType();
            if (propertyType == Boolean.class) {
                viewType =  VIEW_TYPE_BOOLEAN;
            } else if (propertyType == String.class) {
                viewType =  VIEW_TYPE_STRING;
            } else if(Number.class.isAssignableFrom(propertyType)) {
                viewType =  VIEW_TYPE_NUMBER;
            }
        }

        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;

        switch (viewType) {
            case VIEW_TYPE_ENVIRONMENT_NAME:
                View nameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_env_name, parent, false);
                viewHolder = new NameViewHolder(nameView);
                break;
            case VIEW_TYPE_BOOLEAN:
                View checkboxView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_checkbox, parent, false);
                viewHolder = new CheckboxViewHolder(checkboxView);
                break;
            case VIEW_TYPE_NUMBER:
                View numericView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_text, parent, false);
                viewHolder = new NumericViewHolder(numericView);
                break;
            case VIEW_TYPE_STRING:
                View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_text, parent, false);
                viewHolder = new TextViewHolder(textView);
                break;
            default:
                throw new RuntimeException("Unsupported property type");
        }

        return viewHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(setEnvironmentName && position == 0) {
            holder.bind(environment, null);
        } else {
            final int adjustedPosition = position + (setEnvironmentName ? -1 : 0);
            holder.bind(environment, properties.get(adjustedPosition));
        }
    }

    @Override
    public int getItemCount() {
        return properties.size() + (setEnvironmentName ? 1 : 0);
    }

    public static class NameViewHolder extends ViewHolder {
        @Bind(R.id.name_value_layout)
        TextInputLayout textInputLayout;
        @Bind(R.id.name_value)
        EditText value;

        public NameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(final Environment environment,
                         final ConfigurationProperty configurationProperty) {
            value.addTextChangedListener(new SimpleTextWatcher(){
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    environment.setName(s.toString());

                    textInputLayout.setErrorEnabled(s.length() == 0);
                }
            });
        }
    }

    public static class NumericViewHolder extends ViewHolder<Number> {
        @Bind(R.id.key)
        TextView name;
        @Bind(R.id.edit_value)
        EditText value;

        public NumericViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(final Environment environment,
                         final ConfigurationProperty<Number> configurationProperty) {
            name.setText(configurationProperty.getName());
            Number valueNumber = environment.getProperty(configurationProperty.getKey(), configurationProperty.getDefaultValue());
            value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            value.setText(DECIMAL_FORMAT.format(valueNumber));
            value.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    environment.setProperty(configurationProperty.getKey(), Double.valueOf(s.toString()));
                }
            });
        }
    }

    public static class TextViewHolder extends ViewHolder<String> {
        @Bind(R.id.key)
        TextView name;
        @Bind(R.id.edit_value)
        EditText value;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(final Environment environment,
                         final ConfigurationProperty<String> configurationProperty) {
            name.setText(configurationProperty.getName());
            value.setText(environment.getProperty(configurationProperty.getKey(), configurationProperty.getDefaultValue()));
            value.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    environment.setProperty(configurationProperty.getKey(), s.toString());
                }
            });
        }
    }

    public static class CheckboxViewHolder extends ViewHolder<Boolean> {
        @Bind(R.id.key)
        TextView name;
        @Bind(R.id.checkbox_value)
        CheckBox value;

        public CheckboxViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(final Environment environment,
                         final ConfigurationProperty<Boolean> configurationProperty) {
            name.setText(configurationProperty.getName());
            value.setChecked(environment.getProperty(configurationProperty.getKey(), configurationProperty.getDefaultValue()));
            value.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    environment.setProperty(configurationProperty.getKey(), isChecked);
                }
            });
        }
    }

    public abstract static class ViewHolder<T> extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(final Environment environment,
                                  final ConfigurationProperty<T> configurationProperty);
    }
}

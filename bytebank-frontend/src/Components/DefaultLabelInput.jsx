import React from "react";

export const DefaultLabelInput = ({ label, type = "text", onChange }) => {
  const id = label.toLowerCase() + "Input";

  return (
    <div class="mb-3" style={styles.container}>
      <label for={id} className="form-label">
        {label}
      </label>
      <input type={type} className="form-control" onChange={onChange} id={id} />
    </div>
  );
};

const styles = {
  container: {
    display: "flex",
    flexDirection: "column",
    alignItems: "flex-start",
    justifyContent: "flex-start",
    width: "100%",
  },
};
